(ns advent-of-code-2020.day12
  (:require [common :refer [read-data]]
            [fastmath.core :as m]
            [clojure2d.core :as c2d]))

(defn parser
  [s]
  (let [[cmd v] (rest (re-find #"(\w)(\d+)" s))]
    [(first cmd) (Integer/parseInt v)]))

(def data (map parser (read-data 2020 12)))

(def angle->direction {0 \N 90 \E 180 \S 270 \W})

(defn make-step
  [[x y angle :as pos] [cmd v]]
  (case cmd
    \W [(- x v) y angle]
    \E [(+ x v) y angle]
    \N [x (- y v) angle]
    \S [x (+ y v) angle]
    \L [x y (mod (- angle v) 360)]
    \R [x y (mod (+ angle v) 360)]
    \F (make-step pos [(angle->direction angle) v])))

(defn manhattan-distance
  [step-fn data init]
  (let [[x y] (reduce step-fn init data)]
    (int (+ (m/abs x) (m/abs y)))))

(def part-1 (manhattan-distance make-step data [0 0 90]))
;; => 1601

;;

(defn rotate
  [x y angle anti?]
  (let [angle (if anti? (- 360 angle) angle)]
    (case angle
      90 [(- y) x]
      180 [(- x) (- y)]
      270 [y (- x)]
      [x y])))

(defn make-waypoint-step
  [[px py x y] [cmd v]]
  (cond
    (= cmd \F) [(+ px (* v x)) (+ py (* v y)) x y]
    (#{\L \R} cmd) (let [[rx ry] (rotate x y v (= \L cmd))]
                     [px py rx ry])
    :else (case cmd
            \W [px py (- x v) y]
            \E [px py (+ x v) y]
            \N [px py x (- y v)]
            \S [px py x (+ y v)])))

(def part-2 (manhattan-distance make-waypoint-step data [0 0 10 -1]))
;; => 13340

;; vis

(defn make-image
  [steps ^double scale]
  (let [es (/ 10.0 scale)]
    (c2d/with-canvas [c (c2d/canvas 800 800)]
      (c2d/set-background c :black)
      (c2d/translate c 600 600)
      (c2d/scale c scale)
      (c2d/set-stroke c (/ 1.0 scale))
      (c2d/set-color c :red)
      (c2d/ellipse c 0 0 es es)
      (c2d/set-color c 250 250 250 180)
      (doseq [[[sx sy] [ex ey]] steps]
        (c2d/line c sx sy ex ey))
      c)))

(def image-part-1 (make-image (partition 2 1 (reductions make-step [0 0 90] data)) 0.5))
(def image-part-2 (make-image (partition 2 1 (reductions make-waypoint-step [0 0 10 -1] data)) 0.035))

#_(c2d/save image-part-1 "images/advent_of_code_2020/day12_rules_1.jpg")
#_(c2d/save image-part-2 "images/advent_of_code_2020/day12_rules_2.jpg")

(c2d/show-window image-part-1 "Part 1")
(c2d/show-window image-part-2 "Part 2")

