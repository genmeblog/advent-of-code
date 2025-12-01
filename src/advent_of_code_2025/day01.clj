(ns advent-of-code-2025.day01
  (:require [common :refer [read-data]]
            [clojure.string :as str]))

(defn parse-line [line] (-> line (str/escape {\R \+ \L \-}) parse-long))

(def data (map parse-line (read-data 2025 1)))

(defn first-method
  [[current zeros] rotation]
  (let [ncurrent (mod (+ current rotation) 100)]
    [ncurrent (if (zero? ncurrent) (inc zeros) zeros)]))

(defn find-zeros
  [data method]
  (->> data (reduce method [50 0]) last))

(def part-1 (find-zeros data first-method))
;; => 1097

(defn second-method
  [[current zeros] rotation]
  (let [ncurrent (+ current rotation)
        rotations (abs (quot ncurrent 100))
        nzeros (if (or (zero? ncurrent)
                       (and (pos? current) (neg? ncurrent))) (inc rotations) rotations)]
    [(mod ncurrent 100) (+ nzeros zeros)]))

(def part-2 (find-zeros data second-method))
;; => 7101


