(ns advent-of-code-2016.day01
  (:require [common :refer [read-single-line]]
            [clojure.string :as str]
            [fastmath.vector :as v]
            [fastmath.core :as m]
            [clojure.set :as set]
            [clojure2d.core :as c2d]
            [clojure2d.extra.utils :as utils]))

(def data (str/split (read-single-line 2016 1) #", "))

(def directions [[0 1] [1 0] [0 -1] [-1 0]])

(defn next-dir [dir-id instr] (mod ((case (first instr) \L dec \R inc) dir-id) 4))
(defn steps [instr] (Integer/parseInt (subs instr 1)))

(def move (fn [[dir-id pos] instr]
            (let [new-dir-id (next-dir dir-id instr)
                  new-pos (-> (directions new-dir-id)
                              (v/mult (steps instr))
                              (v/add pos))]
              [new-dir-id new-pos])))

(defn distance
  [[x y]]
  (int (+ (m/abs x) (m/abs y))))

(defn process-part-1
  [data]
  (->> data
       (reduce move [0 [0 0]])
       (second)
       (distance)))

(def part-1 (process-part-1 data))

;; => 242

(defn step-by-step
  [[dir-id pos all] instr]
  (let [new-dir-id (next-dir dir-id instr)
        all-steps (->> new-dir-id
                       (directions)
                       (repeat (steps instr))
                       (reductions v/add pos)
                       (rest))
        new-all (reduce (fn [curr-poss new-pos]
                          (if (curr-poss new-pos)
                            (reduced new-pos)
                            (conj curr-poss new-pos))) all all-steps)]
    (if (set? new-all)
      [new-dir-id (last all-steps) (set/union all new-all)]
      (reduced new-all))))

(defn process-part-2
  [data]
  (->> data
       (reduce step-by-step [0 [0.0 0.0] #{[0.0 0.0]}])
       distance))

(def part-2 (process-part-2 data))

;; => 150

;; vizualization

(let [canvas (c2d/canvas 600 600)]
  (c2d/with-canvas [canvas canvas]
    (-> canvas
        (c2d/set-background :white)
        (c2d/translate 100 200)
        (c2d/scale 2.0)
        (c2d/set-color :gray 100)
        (c2d/line -100 0 300 0)
        (c2d/line 0 -100 0 300)
        (c2d/set-color :green 200)
        (c2d/ellipse 0 0 5 5)
        (c2d/set-color :black))
    (doseq [[[x1 y1] [x2 y2]] (->> data
                                   (reductions move [0 [0 0]])
                                   (map second)
                                   (partition 2 1))]
      (c2d/line canvas x1 y1 x2 y2))
    (let [[x y] (reduce step-by-step [0 [0.0 0.0] #{[0.0 0.0]}] data)]
      (c2d/set-color canvas :red 200)
      (c2d/ellipse canvas x y 10 10)))
  (utils/show-image canvas)
  #_(c2d/save canvas "images/advent_of_code_2016/day01.jpg"))
