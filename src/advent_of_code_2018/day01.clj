(ns advent-of-code-2018.day01
  (:require [common :refer [read-data]]))

(def data (map read-string (read-data 2018 1)))

(def part-1 (reduce + data))
;; => 582

(defn freq-dup
  [data]
  (let [freqs (reductions + 0 (cycle data))]
    (reduce (fn [visited freq]
              (if (visited freq)
                (reduced freq)
                (conj visited freq))) #{} freqs)))

(def part-2 (freq-dup data))
;; => 488
