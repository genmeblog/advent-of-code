(ns advent-of-code-2023.day02
  (:require [common :refer [read-data]]
            [clojure.string :as str]
            [fastmath.vector :as v]))

(defn parse-token [t]
  (let [[cnt n] (str/split t #"\s")]
    [(keyword n) (parse-long cnt)]))

(def selector (juxt :red :green :blue))

(defn parse-subset [s]
  (->> (str/split s #",\s")
       (map parse-token)
       (into {})
       (merge {:red 0 :green 0 :blue 0})
       (selector)))

(defn parse-line [l]
  (let [[_ id r] (re-find #"^Game\s(\d+): (.+)" l)
        subsets (str/split r #";\s")]
    [(parse-long id) (map parse-subset subsets)]))

(defn parse-data [data]
  (map parse-line data))

(def data (parse-data (read-data 2023 2)))
;; => [1 ([1 11 3] [5 0 1] [13 5 3] [6 4 1] [16 12 0])]

(defn compare-all [target [id subsets]]
  (if (->> subsets
           (mapcat (partial v/sub target))           
           (every? (complement neg?)))
    id 0))

(defn id-sums [target data]
  (->> data
       (map (partial compare-all target))
       (reduce +)))

(def part-1 (id-sums [12 13 14] data))
;; => 1931

(defn power [game]
  (->> (second game)
       (reduce v/emx)
       (v/prod)))

(defn powers-sum [data]
  (->> data (map power) v/sum int))

(def part-2 (powers-sum data))
;; => 83105
