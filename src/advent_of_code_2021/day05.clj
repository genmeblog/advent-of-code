(ns advent-of-code-2021.day05
  (:require [common :refer [read-data parse]]
            [clojure2d.core :as c2d]
            [clojure2d.extra.utils :as utils]))

(def data (->> (read-data 2021 5)
               (map (partial parse #"(\d+),(\d+) -> (\d+),(\d+)"))))

(defn get-range
  [a b]
  (cond
    (= a b) (repeat a)
    (< a b) (range a (inc b))
    :else (range a (dec b) -1)))

(defn horv [[x1 y1 x2 y2]] (or (= x1 x2) (= y1 y2)))

(defn line [[x1 y1 x2 y2]] (map vector (get-range x1 x2) (get-range y1 y2)))

(defn crosses
  ([data] (crosses data identity))
  ([data condition]
   (->> data
        (filter condition)
        (mapcat line)
        (frequencies)
        (vals)
        (filter #(> % 1))
        (count))))


(def part-1 (crosses data horv))
;; => 6005

(def part-2 (crosses data))
;; => 23864

;; vis

(c2d/with-canvas [c (c2d/canvas 1000 1000)]
  (c2d/set-background c :black)
  (c2d/set-color c :white 150)
  (doseq [[x1 y1 x2 y2] data]
    (c2d/line c x1 y1 x2 y2))
  (utils/show-image c)
  #_(c2d/save c "images/advent_of_code_2021/day05.jpg"))
