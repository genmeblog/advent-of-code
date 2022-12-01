(ns advent-of-code-2022.day01
  (:require [common :refer [read-data-as-blocks]]))

(defn parse [data]
  (map (fn [block]
         (->> block
              (map parse-long)
              (reduce +))) data))

(def data (parse (read-data-as-blocks 2022 1)))

(def part-1 (reduce max data))
;; => 70374

(def part-2 (->> data (sort >) (take 3) (reduce +)))
;; => 204610
