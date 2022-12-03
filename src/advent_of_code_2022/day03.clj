(ns advent-of-code-2022.day03
  (:require [common :refer [read-data]]
            [clojure.set :as set]))

(defn parse [line]
  (map (comp #(- % (if (< % 91) 38 96)) int) line))

(def data (map parse (read-data 2022 3)))

(defn split [line]
  (split-at (/ (count line) 2) line))

(defn common-char [xs]
  (->> xs
       (map set)
       (apply set/intersection)
       (first)))

(defn points [xs]
  (reduce + (map common-char xs)))

(def part-1 (points (map split data)))
;; => 7821

(def part-2 (points (partition 3 data)))
;; => 2752
