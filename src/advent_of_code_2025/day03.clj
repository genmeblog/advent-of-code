(ns advent-of-code-2025.day03
  (:require [common :refer [read-data]]))

(defn parse [s] (map (comp parse-long str) s))

(def data (map parse (read-data 2025 3)))

(defn compare-current
  ([b1 b2] (compare-current b1 b2 []))
  ([[b1a & b1r] [b2a & b2r :as b2] curr]
   (cond
     (not b1a) curr
     (< b1a b2a) (concat curr b2)
     :else (recur b1r b2r (conj curr b1a)))))

(defn joltage
  [size bank]
  (->> (partition size 1 bank)
       (reduce compare-current (repeat size 0))
       (apply str)
       (parse-long)))

(defn total-joltage
  [size data]
  (reduce + (map (partial joltage size) data)))

(def part-1 (total-joltage 2 data))
;; => 17229

(def part-2 (total-joltage 12 data))
;; => 170520923035051
