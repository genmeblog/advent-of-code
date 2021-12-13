(ns advent-of-code-2021.day13
  (:require [common :refer [read-data-as-blocks parse]]
            [clojure2d.core :as c2d]
            [clojure2d.extra.utils :as utils]))

(defn parser
  [[positions instructions]]
  {:positions (map (partial parse #"(\d+),(\d+)") positions)
   :instructions (map (partial parse #".*\s(\w)=(\d+)$") instructions)})

(def data (parser (read-data-as-blocks 2021 13)))

(defn operate [v a] (let [nv (- v a)] (if (pos? nv) (+ a (- nv)) v)))
(defn fold-y [a [x y]] [x (operate y a)])
(defn fold-x [a [x y]] [(operate x a) y])
(def axis-fn {'x fold-x 'y fold-y})

(defn process [{:keys [positions instructions]}]
  (reductions (fn [input [axis a]]
                (->> input
                     (map (partial (axis-fn axis) a)))) positions instructions))

(def part-1 (count (distinct (second (process data)))))
;; => 647

;; part-2, vis

(let [px (last (process data))
      mx (+ 100 (* 20 (reduce max (map first px))))]
  (c2d/with-canvas [c (c2d/canvas mx 200)]
    (c2d/set-background c 0x333333)
    (c2d/set-color c 0xeeeeee 30)
    (doseq [[x y] px]
      (c2d/rect c (+ 40 (* x 20)) (+ 40 (* y 20)) 20 20))
    (utils/show-image c)
    #_(c2d/save c "images/advent_of_code_2021/day13.jpg")))

;; HEJHJRCJ
