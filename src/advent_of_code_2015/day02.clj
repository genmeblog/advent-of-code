(ns advent-of-code-2015.day02
  (:require [clojure.java.io :as io]
            [clojure.string :as s]))

(set! *unchecked-math* :warn-on-boxed)
(set! *warn-on-reflection* true)

(defn parse-line [s] (sort (map read-string (s/split s #"x"))))

(def dimensions (map parse-line (-> (io/resource "day02.txt")
                                    (io/reader)
                                    (line-seq))))

(defn calc-feets
  ^long [[^long d1 ^long d2 ^long d3]]
  (+ (* 3 d1 d2) (* 2 d2 d3) (* 2 d3 d1)))

(defn calc-ribbon
  ^long [[^long d1 ^long d2 ^long d3]]
  (+ d1 d1 d2 d2 (* d1 d2 d3)))

(def part-1 (reduce + (map calc-feets dimensions)))
(def part-2 (reduce + (map calc-ribbon dimensions)))
