(ns advent-of-code-2017.day01
  (:require [common :refer [read-single-line]]))

(def data (read-single-line 2017 01))

(defn process-data
  [offset data]
  (->> data
       (cycle)
       (partition (inc offset) 1)
       (take (count data))
       (filter #(= (first %) (last %)))
       (map first)
       (map (comp read-string str))
       (reduce +)))

(def process-part-1 (partial process-data 1))

(def part-1 (process-part-1 data)) 
;; => 1031

(defn process-part-2
  [data]
  (process-data (/ (count data) 2) data))

(def part-2 (process-part-2 data))
;; => 1080
