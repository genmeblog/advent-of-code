(ns advent-of-code-2025.day09
  (:require [common :refer [read-data unique-pairs get-numbers segment-intersects?]]
            [clojure2d.core :as c2d]
            [clojure2d.core.shape :as shape]
            [clojure2d.extra.utils :as utls]))

(def data (map get-numbers (read-data 2025 9)))

(defn area ^long [[[x1 y1] [x2 y2]]]
  (* (inc (abs (- x1 x2))) (inc (abs (- y1 y2)))))

(defn max-rectangle
  [data]
  (->> data unique-pairs (map area) (reduce max)))

(def part-1 (max-rectangle data))
;; => 4774877510

(defn max-rectangle-on-rg
  [data]
  (let [^java.awt.Shape sh (shape/path data true)]
    (->> (for [[[x1 y1] [x2 y2] :as pair] (unique-pairs data)
               :let [rect (shape/rect (min x1 x2)
                                      (min y1 y2)
                                      (abs (- x1 x2))
                                      (abs (- y1 y2)))]
               :when (.contains sh ^java.awt.geom.Rectangle2D rect)]
           (area pair))
         (reduce max))))

;; slower
(defn max-rectangle-on-rg-2
  [data]
  (let [path (partition 2 1 (take (inc (count data)) (cycle data)))]
    (->> (for [[[^long x1 ^long y1] [^long x2 ^long y2] :as pair] (unique-pairs data)
               :let [[^long x1 ^long x2] (if (< x1 x2) [x1 x2] [x2 x1])
                     [^long y1 ^long y2] (if (< y1 y2) [y1 y2] [y2 y1])
                     x1 (+ x1 0.1) ;; shrink a little bit
                     x2 (- x2 0.1)
                     y1 (+ y1 0.1)
                     y2 (- y2 0.1)
                     s1 [[x1 y1] [x1 y2]]
                     s2 [[x1 y1] [x2 y1]]
                     s3 [[x2 y2] [x2 y1]]
                     s4 [[x2 y2] [x1 y2]]]
               :when (every? (fn [seg] (not (or (segment-intersects? s1 seg)
                                               (segment-intersects? s2 seg)
                                               (segment-intersects? s3 seg)
                                               (segment-intersects? s4 seg)))) path)]
           (area pair))
         (reduce max))))

(comment (max-rectangle-on-rg-2 data))
;; => 1560475800

(def part-2 (max-rectangle-on-rg data))
;; => 1560475800

;; vis

(defn draw-image
  [data]
  (let [size (count data)]
    (c2d/with-canvas [c (c2d/canvas 500 500)]
      (c2d/set-background c [240 240 240])
      (doseq [[[x1 y1] [x2 y2]] (partition 2 1 (take (inc size) (cycle data)))
              :let [x1 (/ x1 200.0)
                    y1 (/ y1 200.0)
                    x2 (/ x2 200.0)
                    y2 (/ y2 200.0)]]
        (c2d/set-color c :green)
        (c2d/line c x1 y1 x2 y2)
        (c2d/set-color c :red)
        (c2d/crect c x1 y1 2 2))
      (c2d/set-color c :red)
      (c2d/save c "images/advent_of_code_2025/day09.png")
      (utls/show-image c))))

(comment
  (draw-image data))
