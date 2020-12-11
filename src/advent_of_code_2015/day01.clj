(ns advent-of-code-2015.day01
  (:require [clojure.java.io :as io]))

(defn convert [c] (case c \( 1 \) -1 0))

(def data (->> "advent_of_code_2015/day01.txt"
               (io/resource)
               (slurp)
               (map convert)))

(def part-1 (reduce + data))
;; => 138

(defn find-step
  [data]
  (->> (reductions + 0 data)
       (take-while (complement neg?))
       (count)))

(def part-2 (find-step data))
;; => 1771
