(ns advent-of-code-2023.day12
  (:require [clojure.string :as str]
            [common :refer [read-data]]))

(defn parse-line [l]
  (let [[rule struct] (str/split l #"\s+")]
    [(vec (str "." rule)) (mapv parse-long (str/split struct #","))]))

(def data (map parse-line (read-data 2023 12)))

(defn match-substring [pattern start hashes dots]
  (let [mid (+ start dots)]
    (and (every? #(or (= % \.) (= % \?)) (subvec pattern start mid))
         (every? #(or (= % \#) (= % \?)) (subvec pattern mid (+ mid hashes))))))

(def find-combinations
  (memoize (fn ([[rule struct]] (find-combinations rule struct 0))
             ([rule struct pos]
              (if-not (seq struct)
                (if (match-substring rule pos 0 (- (count rule) pos)) 1 0)
                (let [hashes (struct 0)]
                  (transduce (comp (filter (partial match-substring rule pos hashes))
                                   (map (fn [d] (+ d pos hashes)))
                                   (map (partial find-combinations rule (subvec struct 1))))
                             + (range 1 (->> (reduce + (dec (count struct)) struct)
                                             (- (count rule) pos -1))))))))))

(defn counts
  ([data] (counts data identity))
  ([data process] (->> (map (comp find-combinations process) data)
                       (reduce +))))

(def part-1 (counts data))
;; => 6958

(defn repeat-rule [[r struct]]
  [(vec (conj (flatten (interpose \? (repeat 5 (subvec r 1)))) \.))
   (vec (flatten (repeat 5 struct)))])

(def part-2 (counts data repeat-rule))
;; => 6555315065024
