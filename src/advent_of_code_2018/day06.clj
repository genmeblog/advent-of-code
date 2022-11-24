(ns advent-of-code-2018.day06
  (:require [common :refer [read-data]]
            [clojure.string :as s]
            [clojure2d.core :as c2d]
            [clojure2d.color :as c]))

(def data (read-data 2018 6))

(defn coords
  [data]
  (map #(let [[x y] (s/split % #",\s")]
          [(read-string x) (read-string y)])
       data))

(defn min-max [[minx miny maxx maxy] [x y]]
  [(min minx x) (min miny y) (max maxx x) (max maxy y)])

(defn bounding-box
  [data]
  (let [crds (coords data)
        [fx fy] (first crds)
        [x1 y1 x2 y2] (reduce min-max [fx fy fx fy] (rest crds))]
    {:coords crds
     :box [[x1 (inc x2)]
           [y1 (inc y2)]]
     :check-box (fn [[x y]]
                  (or (== x1 x) (== x2 x) (== y1 y) (== y2 y)))}))

(defn dist-seq
  [data]
  (let [{:keys [box coords]} (bounding-box data)]
    (for [^long x (apply range (first box))
          ^long y (apply range (second box))
          :let [dists (map (fn [[^long cx ^long cy]]
                             (+ (Math/abs (- x cx))
                                (Math/abs (- y cy)))) coords)]]
      [[x y (reduce + dists)] dists])))

(defn find-minimal-dist
  [lst]
  (let [[_ coord ^long cnt] (reduce (fn [[^long min-dist ^long curr-coord ^long cnt ^long coord] ^long dist]
                                      (let [ncoord (inc coord)]
                                        (cond
                                          (< dist min-dist) [dist coord 0 ncoord]
                                          (== dist min-dist) [dist coord (inc cnt) ncoord]
                                          :else [min-dist curr-coord cnt ncoord])))
                                    [Integer/MAX_VALUE -1 0 0] lst)]
    (when (zero? cnt) coord)))

(defn voronoi
  [data]
  (keep identity
        (pmap (fn [[xy lst]]
                (when-let [id (find-minimal-dist lst)] [id xy]))
              (dist-seq data))))

(defn maximum
  [data]
  (let [on-boundary? (:check-box (bounding-box data))]
    (reduce (fn [^long curr [_ lst]]
              (if (some (comp on-boundary? second) lst)
                curr
                (max curr (count lst)))) 0 (group-by first (voronoi data)))))

(defn region
  [data tdistance]
  (->> (map first (dist-seq data))
       (filter #(< (% 2) tdistance))
       (count)))

(def part-1 (maximum data))
;; => 4186

(def part-2 (region data 10000))
;; => 45509

;; vis

(let [colors (vec (c/resample (c/palette :viridis) 50))
      c (c2d/canvas 800 800)]
  (c2d/with-canvas [c c]
    (c2d/set-background c :black)
    (doseq [[id [x y]] (voronoi data)]
      (c2d/set-color c (colors id))
      (c2d/rect c (* 2 x) (* 2 y) 2 2))
    (c2d/set-color c :black 200)
    (doseq [[x y] (coords data)]
      (c2d/ellipse c (* 2 x) (* 2 y) 5 5)))
  (c2d/show-window {:canvas c})
  #_(c2d/save c "images/advent_of_code_2018/day06.jpg"))
