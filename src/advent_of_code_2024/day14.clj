(ns advent-of-code-2024.day14
  (:require [common :refer [read-data get-numbers prod]]
            [clojure2d.core :as c2d]
            [clojure2d.color :as c]
            [clojure2d.extra.utils :refer [show-image]]
            [fastmath.vector :as v]))

(defn parse [line] (let [[a b c d] (get-numbers line)] [[a b] [c d]]))
(def data (apply map vector (map parse (read-data 2024 14))))

(defn move1 [w h steps [sx sy] [dx dy]]
  [(mod (+ sx (* dx steps)) w)
   (mod (+ sy (* dy steps)) h)])

(defn move [w h steps [xy dxy]] [(map (partial move1 w h steps) xy dxy) dxy])

(defn ->quadrant [w h]
  (let [w1 (quot w 2)
        h1 (quot h 2)]
    (fn [[x y]] (cond (and (< x w1) (< y h1)) 1
                     (and (< x w1) (> y h1)) 2
                     (and (> x w1) (< y h1)) 3
                     (and (> x w1) (> y h1)) 4
                     :else 0))))

(defn count-by-quadrants [data w h]
  (let [q (->quadrant w h)]
    (->> data
         (move w h 100)
         (first)
         (map q)
         (filter pos?)
         (frequencies)
         (vals)
         (prod))))

(def part-1 (count-by-quadrants data 101 103))
;; => 224357412


;; part-2

(defn draw-state
  ([c poss] (draw-state c nil poss))
  ([c frame poss]
   (c2d/set-background c (c/gray 20))
   (c2d/set-color c :limegreen)
   (doseq [[x y] (first poss)]
     (c2d/rect c (* x 5) (* y 5) 5 5))
   (when frame
     (c2d/set-color c :red)
     (c2d/text c (str "frame: " frame) 10 20))
   c))

;; exploration

(defn draw [c _ frame poss]
  (draw-state c frame poss)
  (move 101 103 101 poss))

;; offset: 13 - vertical, cycle - 101
;; offset: 79 - horizontal, cycle - 103

(comment (c2d/show-window {:canvas (c2d/canvas (* 101 5) (* 103 5))
                           :window-name "Day 14 - Easter Egg"
                           :draw-fn draw
                           :fps 10
                           :draw-state (move 101 103 (+ 13) data)}))

;; solution

(def part-2 (+ 13 (* 101 70)))
;; => 7083

(comment
  (let [frame (+ 13 (* 101 70))]
    (c2d/with-canvas-> (c2d/canvas (* 101 5) (* 103 5))
      (draw-state (move 101 103 frame data))
      (c2d/save "images/advent_of_code_2024/day14.jpg")
      (show-image))))
