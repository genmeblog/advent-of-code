(ns advent-of-code-2022.day06
  (:require [common :refer [read-single-line]]))

(def data (read-single-line 2022 6))

(defn marker-id
  [data marker-length]
  (->> (partition marker-length 1 data)
       (map (comp count set))
       (map-indexed vector)
       (sort-by second >)
       (first)
       (reduce +)))

(def part-1 (marker-id data 4))
;; => 1238

(def part-2 (marker-id data 14))
;; => 3037
