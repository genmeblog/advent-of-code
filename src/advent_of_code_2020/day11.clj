(ns advent-of-code-2020.day11
  (:require [common :refer [read-data]]
            [fastmath.core :as m]
            [fastmath.vector :as v]
            [clojure2d.core :as c2d]
            [fastmath.random :as r]
            [clojure.string :as str]
            [clojure2d.color :as c]))

(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)
(m/use-primitive-operators)

(defrecord Seats [^long rows ^long cols changed? data index])

(defn ->seats
  [input]
  (->Seats (count input) (count (first input)) true (vec (mapcat identity input)) nil))

(def seats (->seats (read-data 2020 11)))

(defn at
  [^Seats input ^long row ^long col]
  ((.data input) (+ col (* row (.cols input)))))

(defn outside?
  [^Seats input ^long row ^long col]
  (or (neg? row) (neg? col)
      (== row (.rows input)) (== col (.cols input))))

(defn occupation
  ^long [^Seats input ^long row ^long col]
  (or (and (or (outside? input row col)
               (#{\. \L} (at input row col))) 0) 1))

(defn neighbours
  ^long [^Seats input ^long row ^long col]
  (+ (occupation input (dec row) (dec col))
     (occupation input (dec row) col)
     (occupation input (dec row) (inc col))
     (occupation input row (dec col))
     (occupation input row (inc col))
     (occupation input (inc row) (dec col))
     (occupation input (inc row) col)
     (occupation input (inc row) (inc col))))

(defn step-near
  [^Seats input]
  (let [d (for [row (range (.rows input))
                col (range (.cols input))
                :let [v (at input row col)
                      n (neighbours input row col)]]
            (cond
              (and (>= n 4) (= v \#)) [true \L]
              (and (zero? n) (= v \L)) [true \#]
              :else [false v]))]
    (assoc input :data (mapv second d) :changed? (some first d))))

(defn occupation-state
  [data step-fn]
  (->> data
       (iterate step-fn)
       (remove :changed?)
       (first)
       (:data)
       (filter #{\#})
       (count)))

(def part-1 (time (occupation-state seats step-near)))
;; => 2359

;;

(defn process-direction
  [data pos direction]
  (->> pos
       (iterate (fn [curr-pos] (v/add curr-pos direction)))
       (next)
       (reduce (fn [curr curr-pos]
                 (let [row (curr-pos 0)
                       col (curr-pos 1)]
                   (cond
                     (outside? data row col) (reduced curr)
                     (= \L (at data row col)) (reduced curr-pos)
                     :else curr))) nil)))

(def directions
  (map (partial apply v/vec2) [[-1 -1] [-1 0] [-1 1] [0 1] [0 -1] [1 -1] [1 0] [1 1]]))

(defn process-directions
  [data pos]
  (->> directions
       (map (partial process-direction data pos))
       (filter identity)))

(defn build-index
  [^Seats input]
  (into {} (for [row (range (.rows input))
                 col (range (.cols input))
                 :when (= \L (at input row col))
                 :let [pos (v/vec2 row col)]]
             [pos (process-directions input pos)])))

(defn with-index
  [seats]
  (assoc seats :index (build-index seats)))

(defn step-far
  [^Seats input]
  (let [d (for [^long row (range (.rows input))
                ^long col (range (.cols input))
                :let [v (at input row col)]]
            (if-let [view ((.index input) (v/vec2 row col))]
              (let [n (count (filter (fn [pos] (= \# (at input (pos 0) (pos 1)))) view))]
                (cond
                  (and (>= n 5) (= v \#)) [true \L]
                  (and (zero? n) (= v \L)) [true \#]
                  :else [false v]))
              [false v]))]
    (assoc input :data (mapv second d) :changed? (some first d))))

(def part-2 (time (occupation-state (with-index seats) step-far)))
;; => 2131

;; vis

(def occupations-near (iterate step-near seats))
(def occupations-far (iterate step-far (with-index seats)))

;; font used: https://fontawesome.com/cheatsheet/free/solid

(defn draw
  [c occupations id]
  (let [{:keys [^long rows ^long cols] :as all} (nth occupations id)]
    (c2d/with-canvas [c c]
      (-> c
          (c2d/translate 7 12)
          (c2d/set-font "Font Awesome 5 Free Solid")
          (c2d/set-font-attributes 8)
          (c2d/set-background :black))
      (doseq [^long row (range rows)
              ^long col (range cols)
              :let [v (at all row col)
                    [color s] (case v
                                \L [:gray ""]
                                \# [:white (rand-nth ["" ""])]
                                [(c/color 80 80 80) ""])]]
        (c2d/set-color c color)
        (c2d/text c s (* row 10) (* col 10))))))

(def canvas (c2d/canvas 1000 930))

(def window (c2d/show-window canvas "occupation"))

(draw canvas occupations-near 20)

(c2d/save canvas "images/advent_of_code_2020/day11_far_20.jpg")



