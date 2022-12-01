(ns advent-of-code-2022.day01
  (:require [common :refer [read-data-as-blocks]]))

(defn parse [data]
  (->> data
       (map (fn [block]
              (->> block
                   (map parse-long)
                   (reduce +))))
       (sort >)))

(def data (parse (read-data-as-blocks 2022 1)))

(def part-1 (first data))
;; => 70374

(def part-2 (->> data (take 3) (reduce +)))
;; => 204610
