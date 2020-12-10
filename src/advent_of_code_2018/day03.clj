(ns advent-of-code-2018.day03
  (:require [clojure.java.io :as io]
            [clojure.set :refer :all]))

(set! *unchecked-math* :warn-on-boxed)
(set! *warn-on-reflection* true)

(defn claim-parser
  "Parse claims, return rect coordinates."
  [line]
  (let [[id ^long x ^long y ^long w ^long h] (map read-string (rest (re-find #"#(\d+)\s@\s(\d+),(\d+):\s(\d+)x(\d+)" line)))]
    [id x y (+ x w) (+ y h)]))

(def claims
  (delay (mapv claim-parser (-> (io/resource "day03.txt")
                                (io/reader)
                                (line-seq)))))

(defn overlap
  "Find overlaps"
  [[id1 ^long r1x1 ^long r1y1 ^long r1x2 ^long r1y2] [id2 ^long r2x1 ^long r2y1 ^long r2x2 ^long r2y2]]
  (when (not= id1 id2)
    (let [left (max r1x1 r2x1)
          right (min r1x2 r2x2)
          top (max r1y1 r2y1)
          bottom (min r1y2 r2y2)]
      (when (and (< left right) (< top bottom))
        [left top right bottom]))))

(def overlaps-map
  (delay (reduce #(->> (map (partial overlap %2) @claims)
                       (filter some?)
                       (seq)
                       (assoc %1 (first %2))) {} @claims)))

(defn overlap->set
  "Convert common rectangle to set of coordinates"
  [[x1 y1 x2 y2]]
  (set (for [x (range x1 x2)
             y (range y1 y2)]
         [x y])))

(def inches-overlap
  (delay (count (->> (vals @overlaps-map)
                     (mapcat identity)
                     (mapv overlap->set)
                     (reduce union #{})))))

;; solution

(time {:overlapping @inches-overlap
       :not-overlapping-id (ffirst (filter (comp nil? second) @overlaps-map))})
;; => {:overlapping 118539, :not-overlapping-id 1270}
