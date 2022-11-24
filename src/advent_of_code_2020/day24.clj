(ns advent-of-code-2020.day24
  (:require [common :refer [read-data]]
            [clojure.string :as str]
            [clojure.set :refer [intersection]]
            [fastmath.vector :as v]
            [clojure2d.core :as c2d]
            [fastmath.grid :as g]))

(defn fix
  [line]
  (-> line
      (str/replace #"se" "1")
      (str/replace #"sw" "2")
      (str/replace #"nw" "4")
      (str/replace #"ne" "5")))

(def coords {\e (v/vec2 1 0)
             \1 (v/vec2 0 1)
             \2 (v/vec2 -1 1)
             \w (v/vec2 -1 0)
             \4 (v/vec2 0 -1)
             \5 (v/vec2 1 -1)})

(def vcoords (vals coords))

(def data (map fix (read-data 2020 24)))

(defn flip
  [line]
  (reduce (fn [curr step]
            (v/add curr (coords step))) (v/vec2 0 0) line))

(defn flip-tiles
  [data]
  (reduce (fn [curr line]
            (let [pos (flip line)]
              (if (curr pos)
                (disj curr pos)
                (conj curr pos)))) #{} data))

(def part-1 (count (flip-tiles data)))
;; => 266

(defn step
  [data]
  (let [[mnq mnr] (v/add (reduce v/emn data) (v/vec2 -1 -1))
        [mxq mxr] (v/add (reduce v/emx data) (v/vec2 2 2))]
    (reduce (fn [curr [pos ^long adj]]
              (if (or (and (data pos)
                           (or (#{1 2} adj)))
                      (and (not (data pos))
                           (= adj 2)))
                (conj curr pos)
                curr)) #{} (for [q (range mnq mxq)
                                 r (range mnr mxr)
                                 :let [pos (v/vec2 q r)]]
                             [pos (->> vcoords
                                       (map (partial v/add pos))
                                       (set)
                                       (intersection data)
                                       (count))]))))


(defn gol
  [data n]
  (count (nth (iterate step (flip-tiles data)) n)))

(def part-2 (gol data 100))
;; => 3627

;; vis

(defn vis
  [data]
  (let [grid (g/grid :pointy-hex 8)
        poss (map (fn [v]
                    (g/cell->anchor grid v)) data)]
    (c2d/with-canvas [c (c2d/canvas 900 900)]
      (c2d/translate c 450 450)
      (c2d/set-background c :white)
      (c2d/set-color c :black)
      (doseq [[x y] poss]
        (c2d/filled-with-stroke c :black :gray c2d/grid-cell grid x y))
      c)))

(def vis-res (vis (nth (iterate step (flip-tiles data)) 100)))

(c2d/show-window {:canvas vis-res})

(comment
  (c2d/save vis-res "images/advent_of_code_2020/day24_100.jpg"))

