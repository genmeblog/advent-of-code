(ns advent-of-code-2021.day11
  (:require [common :refer [read-data]]
            [clojure2d.core :as c2d]
            [clojure2d.color :as c]
            [fastmath.interpolation :as i]
            [fastmath.core :as m]))

(defn positions [offset] (for [x (range offset) y (range offset)] [x y]))
(defn not-all-flashed? [grid] (some #(> % 9) (flatten grid)))
(defn increment [grid ps] (reduce #(update-in %1 %2 inc) grid ps))

(defn around
  [offset [px py]]
  (for [x [-1 0 1] y [-1 0 1]        
        :let [nx (+ px x)
              ny (+ py y)]
        :when (and (< -1 nx offset)
                   (< -1 ny offset))]
    [nx ny]))

(defn cascade-light
  [offset grid]
  (let [ngrid (reduce (fn [g pos]
                        (assoc-in (->> pos
                                       (around offset)
                                       (remove #(zero? (get-in g %)))
                                       (increment g)) pos 0)) grid (filter #(> (get-in grid %) 9) (positions offset)))]
    (if (not-all-flashed? ngrid)
      (recur offset ngrid)
      ngrid)))

(defn iterator
  [{:keys [offset grid] :as data}]
  (->> (positions offset)
       (increment grid)
       (cascade-light offset)
       (assoc data :grid)))

(defn parse
  [data]
  (->> {:offset (count (first data))
        :grid (mapv #(mapv (comp read-string str) %) data)}
       (iterate iterator)
       (map-indexed vector)))

(def data (parse (read-data 2021 11)))

(defn total-flashes
  ([data] (total-flashes data 100))
  ([data flashes]
   (->> data
        (take (inc flashes))
        (map (comp :grid second))
        (flatten)
        (filter zero?)
        (count))))

(def part-1 (total-flashes data))
;; => 1585

(defn full-flash
  [data]
  (->> data
       (drop-while #(->> (second %)
                         (:grid)
                         (flatten)
                         (some pos?)))
       (ffirst)))

(def part-2 (full-flash data))
;; => 382

;; vis

(def grad (c/gradient [:navy :orange :white]))
(defn interpolator
  [grid]
  (let [ps (range 9)
        vs (partition 9 (for [x ps y ps] (get-in grid [x y])))]
    (i/cubic-2d ps ps vs )))

(def canvas (c2d/with-canvas-> (c2d/canvas 600 600)
            (c2d/set-background :navy)))
(def window (c2d/show-window {:canvas canvas}))

(dotimes [t 10 #_400]
  (let [i (interpolator (:grid (second (nth data t))))]
    (c2d/with-canvas [c canvas]
      (c2d/set-background c :navy 20)
      (doseq [x (range 500)
              y (range 500)
              :let [xx (m/norm x 0 500 0 9)
                    yy (m/norm y 0 500 0 9)]]
        (c2d/set-color c (grad (/ (i xx yy) 9.0)) 100)
        (c2d/rect c (+ 50 x) (+ 50 y) 1 1))
      #_ (c2d/save c (format "images/advent_of_code_2021/frames/%06d.png" t)))))

;; cd frames
;; for i in *.png; do convert $i $i.gif; done
;; gifsicle -d 6 --loop --colors 256 *.gif > ../day11.gif
;; cd ..
;; rm -r frames
