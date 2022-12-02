(ns advent-of-code-2019.day07
  (:require [advent-of-code-2019.day05 :as day5]
            [common :refer [read-single-line]]
            [clojure.string :as s]))

(def memory (mapv read-string
                (-> (read-single-line 2019 7)
                    (s/split #","))))

(defn run-acs
  [memory [a b c d e]]
  (day5/run-part memory [e (day5/run-part memory [d (day5/run-part memory [c (day5/run-part memory [b (day5/run-part memory [a 0])])])])]))

(defn permutations [s]
  (if (seq (rest s))
    (mapcat identity (for [x s]
                       (map #(cons x %) (permutations (disj (set s) x)))))
    [s]))

(def part-1 (reduce max (pmap (partial run-acs memory) (permutations (range 5)))))
