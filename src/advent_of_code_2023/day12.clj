(ns advent-of-code-2023.day12
  (:require [clojure.string :as str]
            [common :refer [read-data]]))

(defn parse-line [l]
  (let [[rule struct] (str/split l #"\s+")]
    [(vec (str "." rule)) (map parse-long (str/split struct #","))]))

(def data (map parse-line (read-data 2023 12)))

(defn match-substring [pattern start dots hashes]
  (let [mid (+ start dots)]
    (and (every? #(or (= % \.) (= % \?)) (subvec pattern start mid))
         (every? #(or (= % \#) (= % \?)) (subvec pattern mid (+ mid hashes))))))

(def find-combinations
  (memoize (fn ([[rule struct]] (find-combinations rule struct 0))
             ([rule struct pos]
              (if-not (seq struct)
                (if (match-substring rule pos (- (count rule) pos) 0) 1 0)
                (let [sum (reduce + (dec (count struct)) struct)
                      mx (- (count rule) sum pos)
                      hashes (first struct)]
                  (->> (for [dots (range 1 (inc mx))
                             :when (match-substring rule pos dots hashes)]
                         (+ pos dots hashes))
                       (map (partial find-combinations rule (rest struct)))
                       (reduce +))))))))

(defn counts
  ([data] (counts data identity))
  ([data process]
   (->> (map (comp find-combinations process) data)
        (reduce +))))

(def part-1 (counts data))
;; => 6958

(defn repeat-rule [[r struct]]
  [(vec (conj (flatten (interpose \? (repeat 5 (subvec r 1)))) \.))
   (flatten (repeat 5 struct))])

(def part-2 (counts data repeat-rule))
;; => 6555315065024
