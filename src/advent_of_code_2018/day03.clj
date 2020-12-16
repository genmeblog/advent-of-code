(ns advent-of-code-2018.day03
  (:require [common :refer [read-data]]
            [clojure.set :refer [union]]))

(defn claim-parser
  "Parse claims, return rect coordinates."
  [line]
  (let [[id x y w h] (map read-string (rest (re-find #"#(\d+) @ (\d+),(\d+):\s(\d+)x(\d+)" line)))]
    [id x y (+ x w) (+ y h)]))

(def data (map claim-parser (read-data 2018 3)))

(defn overlap
  "Find overlaps"
  [[id1 r1x1 r1y1 r1x2 r1y2] [id2 r2x1 r2y1 r2x2 r2y2]]
  (when (not= id1 id2)
    (let [left (max r1x1 r2x1)
          right (min r1x2 r2x2)
          top (max r1y1 r2y1)
          bottom (min r1y2 r2y2)]
      (when (and (< left right) (< top bottom))
        [left top right bottom]))))

(defn overlaps-map
  [data]
  (reduce #(->> (map (partial overlap %2) data)
                (filter some?)
                (assoc %1 (first %2))) {} data))

(defn overlap->set
  "Convert common rectangle to set of coordinates"
  [[x1 y1 x2 y2]]
  (set (for [x (range x1 x2)
             y (range y1 y2)]
         [x y])))

(defn inches-overlap
  [data]
  (count (->> data
              (overlaps-map)
              (vals)
              (mapcat identity)
              (map overlap->set)
              (reduce union #{}))))

(def part-1 (inches-overlap data))
;; => 118539

(defn not-overlapping
  [data]
  (->> data
       (overlaps-map)
       (filter (complement (comp seq second)))
       (ffirst)))

(def part-2 (not-overlapping data))
;; => 1270
