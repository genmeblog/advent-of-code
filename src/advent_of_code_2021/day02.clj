(ns advent-of-code-2021.day02
  (:require [common :refer [read-data parse]]))

(def re #"(\w+) (\d+)")

(def data (->> (read-data 2021 2)
               (map (partial parse re))))

(defn process
  [data]
  (->> (reduce (fn [[x y] [i d]]
                 (case i
                   forward [(+ x d) y]
                   down [x (+ y d)]
                   up [x (- y d)])) [0 0] data)
       (reduce *)))

(def part-1 (process data))
;; => 1947824

(defn process-aim
  [data]
  (->> (reduce (fn [[x y a] [i d]]
                 (case i
                   forward [(+ x d) (+ y (* a d)) a]
                   down [x y (+ a d)]
                   up [x y (- a d)])) [0 0 0] data)
       (butlast)
       (reduce *)))

(def part-2 (process-aim data))
;; => 1813062561
