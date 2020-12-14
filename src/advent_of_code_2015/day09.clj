(ns advent-of-code-2015.day09
  (:require [common :refer [read-data]]))

(defn parse
  [line]
  (let [[from to dist] (rest (re-find #"(\w+) to (\w+) = (\d+)" line))
        d (read-string dist)]
    [[[to from] d] [[from to] d]]))

(def data (into {} (mapcat parse (read-data 2015 9))))

;; https://www.geeksforgeeks.org/travelling-salesman-problem-implementation-using-backtracking/

(defn tsp
  [graph cities first-city visited curr-city cost ans f]
  (if (and (= (count cities) (count visited)) (not= first-city curr-city))
    (f ans cost)
    (reduce (fn [ans city]
              (if (and (contains? graph [curr-city city])
                       (not (visited city)))
                (tsp graph cities first-city (conj visited city) city (+ cost (graph [curr-city city])) ans f)
                ans)) ans cities)))

(defn find-path
  [init data]
  (let [cities (distinct (map first (keys data)))
        f (if (neg? init) max min)]
    (reduce #(tsp data cities %2 #{%2} %2 0 %1 f) init cities)))

(def find-shortest (partial find-path Long/MAX_VALUE))
(def find-longest (partial find-path Long/MIN_VALUE))

(def part-1 (find-shortest data))
;; => 141

(def part-2 (find-longest data))
;; => 736
