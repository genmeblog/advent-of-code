(ns advent-of-code-2024.day04
  (:require [common :refer [read-data]]))

(def data (mapv vec (read-data 2024 04)))

(defn add [[x y] [a b]] [(+ x a) (+ y b)])

(def xmas-offsets [[[0 0] [1 0] [2 0] [3 0]]
                 [[0 0] [0 1] [0 2] [0 3]]
                 [[0 0] [1 1] [2 2] [3 3]]
                 [[0 0] [1 -1] [2 -2] [3 -3]]])

(def xmas-patterns (set (map seq ["XMAS" "SAMX"])))

(defn with-offsets
  [data xy offs]
  (->> offs
       (map (partial add xy))
       (mapv (partial get-in data))))

(defn count-patterns
  [data offsets patterns]
  (let [ids (range (count data))]
    (->> (for [row ids col ids offs offsets] (with-offsets data [row col] offs))
         (filter patterns)
         (count))))

(def part-1 (count-patterns data xmas-offsets xmas-patterns))
;; => 2642

(def x-mas-offsets [[[0 0] [0 2] [1 1] [2 0] [2 2]]])
(def x-mas-patterns (set (map seq ["SSAMM" "MMASS" "MSAMS" "SMASM"])))

(def part-2 (count-patterns data x-mas-offsets x-mas-patterns))
;; => 1974

