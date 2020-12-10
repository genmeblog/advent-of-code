(ns advent-of-code-2015.day01
  (:require [clojure.java.io :as io]))

(set! *unchecked-math* :warn-on-boxed)
(set! *warn-on-reflection* true)

(defn convert [c] (case c \( 1 \) -1 0))

(def data (map convert (slurp (io/resource "day01.txt"))))

(def part-1 (reduce + data))

(defn find-step
  [d]
  (->> (reductions + 0 d)
       (map-indexed vector)
       (drop-while (complement (comp neg? second)))
       (ffirst)))

(def part-2 (find-step data))
