(ns advent-of-code-2015.day02
  (:require [common :refer [read-data]]
            [clojure.string :as s]))

(defn parse-line [s] (sort (map read-string (s/split s #"x"))))

(def dimensions (map parse-line (read-data 2015 2)))

(defn calc-feets [[d1 d2 d3]]
  (+ (* 3 d1 d2) (* 2 d2 d3) (* 2 d3 d1)))

(def part-1 (reduce + (map calc-feets dimensions)))
;; => 1606483

(defn calc-ribbon [[d1 d2 d3]]
  (+ d1 d1 d2 d2 (* d1 d2 d3)))

(def part-2 (reduce + (map calc-ribbon dimensions)))
;; => 3842356
