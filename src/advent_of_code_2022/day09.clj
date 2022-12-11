(ns advent-of-code-2022.day09
  (:require [common :refer [read-data split-line]]
            [fastmath.vector :as v]
            [clojure2d.core :as c2d]
            [clojure2d.extra.utils :as utils]))

(def moves {"D" (v/vec2 0 1) "U" (v/vec2 0 -1) "L" (v/vec2 -1 0) "R" (v/vec2 1 0)})

(defn parse [line]
  (let [[a b] (split-line line)]
    (repeat (parse-long b) (moves a))))

(def data (mapcat parse (read-data 2022 9)))

(defn move-knot
  [h t]
  (let [diff (v/fmap (v/div (v/sub t h) 2) int)]
    (if (v/is-zero? diff) t (v/add h diff))))

(defn move-rope
  [knots data]
  (->> data
       (reductions (fn [[h & ts] cmd]
                     (reductions move-knot (v/add h cmd) ts)) (repeat (inc knots) (v/vec2 0.0 0.0)))
       (map last)))

(defn last-knot-count
  [tails data]
  (-> (move-rope tails data) distinct count))

(def part-1 (last-knot-count 1 data))
;; => 6522

(def part-2 (last-knot-count 9 data))
;; => 2717


;; vis

(c2d/with-canvas [c (c2d/canvas 600 600)]
  (c2d/set-background c 0)
  (c2d/translate c 300 400)
  (c2d/set-color c :red)
  (c2d/ellipse c 0 0 10 10)
  (doseq [[id col] (map-indexed vector
                                ;; [:white]
                                [:blue :blue :blue :blue :blue :blue :blue :blue :white]
                                ;; (conj (vec (repeat 500 :blue)) :white)
                                )
          :let [p (move-rope (inc id) data)
                cnt (count (distinct p))]
          :when (> cnt 1)]
    ;; (println (inc id) cnt)
    (c2d/set-color c col 100)
    (c2d/path c p))
  ;; (c2d/save c "images/advent_of_code_2022/day09d.jpg")
  (utils/show-image c))
