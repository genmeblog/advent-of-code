(ns advent-of-code-2015.day01
  (:require [common :refer [read-single-line]]))

(defn convert [c] (case c \( 1 \) -1 0))

(def data (map convert (read-single-line 2015 1)))

(def part-1 (reduce + data))
;; => 138

(defn find-step [data]
  (->> (reductions + 0 data)
       (take-while (complement neg?))
       (count)))

(def part-2 (find-step data))
;; => 1771
