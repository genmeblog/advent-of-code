(ns advent-of-code-2016.day06
  (:require [common :refer [read-data]]))

(def data (read-data 2016 6))

(defn decode
  [data selector]
  (->> data
       (apply map vector)
       (map frequencies)
       (map (partial sort-by second))
       (map (comp first selector))
       (apply str)))

(def part-1 (decode data last))
;; => "xhnqpqql"

(def part-2 (decode data first))
;; => "brhailro"
