(ns advent-of-code-2021.day01
  (:require [common :refer [parse read-data]]))

(def data (parse (read-data 2021 1)))

(defn count-incresing
  [data]
  (->> data
       (partition 2 1)
       (map (partial reduce -))
       (filter neg?)
       (count)))

(def part-1 (count-incresing data))
;; => 1167

(defn count-incresing-3
  [data]
  (->> data
       (partition 3 1)
       (map (partial reduce +))
       (count-incresing)))

(def part-2 (count-incresing-3 data))
;; => 1130
