(ns advent-of-code-2017.day03
  (:require [fastmath.core :as m]))

(def target 312051)

(defn pos
  "Which ring and offset on the ring"
  [in]
  (let [f (dec (m/ceil (* 0.5 (inc (m/sqrt in)))))]
    [f (- in (m/sq (dec (* 2.0 f))))]))

;;           6
;; 4 3 2 3 4 5 6
;; 3 2 1 2 3 4
;; 2 1 0 1 2 3
;; 3 2 1 2 3 4
;; 4 3 2 3 4 5
;; ...

(defn ring-dist
  [in]
  (if (= 1 in)
    0
    (let [[ring off] (pos in)
          r (* 2.0 ring)]
      (-> (range r ring -1)
          (concat (range ring r))
          (cycle)
          (nth off)
          (int)))))

(def part-1 (ring-dist target))
;; => 430

;;

(defn ring->poss
  [id]
  (let [r (rest (range (- id) (inc id)))]
    (concat (map vector (repeat id) r)
            (map vector (map - r) (repeat id))
            (map vector (repeat (- id)) (map - r))
            (map vector r (repeat (- id))))))

(defn around
  [[x y]]
  [[(inc x) y]
   [(inc x) (inc y)]
   [(inc x) (dec y)]
   [(dec x) y]
   [(dec x) (inc y)]
   [(dec x) (dec y)]
   [x (inc y)]
   [x (dec y)]])

(defn spiral2
  [target]
  (reduce (fn [buff pos]
            (let [res (->> (around pos)
                           (select-keys buff)
                           (vals)
                           (reduce +))]
              (if (> res target)
                (reduced res)
                (assoc buff pos res)))) {[0 0] 1} (mapcat ring->poss (range))))

(def part-2 (spiral2 target))
;; => 312453


