(ns advent-of-code-2023.day07
  (:require [common :refer [read-data]]
            [clojure.string :as str]))

(def types {[1 1 1 1 1] 0 [1 1 1 2] 1 [1 2 2] 2 [1 1 3] 3 [2 3] 4 [1 4] 5 [5] 6})

;; convert cards to hex numbers
(def escapes-part1 {\A \E \K \D \Q \C \J \B \T \A})
(def escapes-part2 {\A \E \K \D \Q \C \J \1 \T \A})

(defn score-type [cards] (-> cards frequencies vals sort vec types))

(defn score-best [cards]
  (->> "123456789ABCDE"
       (map (fn [c] (score-type (str/escape cards {\1 c}))))
       (reduce max)))

(defn parse-line [escape-map scoring l]
  (let [[cards score] (str/split l #"\s+")
        ncards (str/escape cards escape-map)]
    [ncards (scoring ncards) (parse-long score)]))

(defn compare-cards [[c1 t1] [c2 t2]]
  (let [step1 (compare t1 t2)]
    (if-not (zero? step1)
      step1
      (compare (Integer/parseInt c1 16)
               (Integer/parseInt c2 16)))))

(def data (read-data 2023 7))

(defn score-game [data escape-map scoring]
  (->> data
       (map (partial parse-line escape-map scoring))
       (sort compare-cards)
       (map-indexed (fn [id c] (* (inc id) (last c))))
       (reduce +)))

(def part-1 (score-game data escapes-part1 score-type))
;; => 253910319

(def part-2 (score-game data escapes-part2 score-best))
;; => 254083736
