(ns advent-of-code-2018.day06
  (:require [clojure.java.io :as io]
            [clojure.string :as s]
            [clojure2d.core :refer :all]
            [fastmath.random :as r]
            [clojure2d.color :as c]))

(set! *unchecked-math* :warn-on-boxed)
(set! *warn-on-reflection* true)

(def coords (delay (mapv #(let [[x y] (s/split % #",\s")]
                            [(read-string x) (read-string y)])
                         (->> "day06.txt"
                              (io/resource)
                              (io/reader)
                              (line-seq)))))

(defn min-max [[^long minx ^long miny ^long maxx ^long maxy] [^long x ^long y]]
  [(min minx x) (min miny y) (max maxx x) (max maxy y)])

(def bounding-box
  (delay (let [[fx fy] (first @coords)
               [^long x1 ^long y1 ^long x2 ^long y2] (reduce min-max [fx fy fx fy] (rest @coords))]
           {:box [[x1 (inc x2)]
                  [y1 (inc y2)]]
            :check-box (fn [[^long x ^long y]]
                         (or (== x1 x) (== x2 x) (== y1 y) (== y2 y)))})))

(def dist-seq
  (delay (for [^long x (apply range (first (:box @bounding-box)))
               ^long y (apply range (second (:box @bounding-box)))
               :let [dists (mapv (fn [[^long cx ^long cy]]
                                   (+ (Math/abs (- x cx))
                                      (Math/abs (- y cy)))) @coords)]]
           [[x y (reduce + dists)] dists])))

(defn find-minimal-dist [lst]
  (let [[_ coord ^long cnt] (reduce (fn [[^long min-dist ^long curr-coord ^long cnt ^long coord] ^long dist]
                                      (let [ncoord (inc coord)]
                                        (cond
                                          (< dist min-dist) [dist coord 0 ncoord]
                                          (== dist min-dist) [dist coord (inc cnt) ncoord]
                                          :else [min-dist curr-coord cnt ncoord])))
                                    [Integer/MAX_VALUE -1 0 0] lst)]
    (when (zero? cnt) coord)))

(def voronoi (delay (keep identity
                          (mapv (fn [[xy lst]]
                                  (when-let [id (find-minimal-dist lst)] [id xy]))
                                @dist-seq))))

(defn maximum []
  (let [on-boundary? (:check-box @bounding-box)]
    (reduce (fn [^long curr [_ lst]]
              (if (some (comp on-boundary? second) lst)
                curr
                (max curr (count lst)))) 0 (group-by first @voronoi))))

(defn region []
  (->> (map first @dist-seq)
       (filter #(< ^long (% 2) 10000))
       (count)))

(time {:largest-area (maximum)
       :region-area (region)})
;; => {:largest-area 4186, :region-area 45509}

;; draw

(let [colors (mapv #(c/gray (* 255 ^double %)) (r/->seq r/default-rng 50))
      c (canvas 400 400)]
  (with-canvas [c c]
    (set-background c :black)
    (doseq [[id [x y]] @voronoi]
      (set-color c (colors id))
      (point c x y))
    (set-color c :red 200)
    (doseq [[x y] @coords]
      (ellipse c x y 3 3)))
  (show-window {:canvas c})
  (save c "images/day6.jpg"))
