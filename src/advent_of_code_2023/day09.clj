(ns advent-of-code-2023.day09
  (:require [common :refer [read-data]]))

(defn parse-lines [l]
  (->> (re-seq #"[\-\d]+" l)
       (map parse-long)))

(def data (map parse-lines (read-data 2023 9)))

(defn process-history [h]
  (->> (partition 2 1 h)
       (map (partial apply -))))

(defn not-zero? [h] (not (every? zero? h)))

(defn predict [reverse? h]
  (->> (if reverse? (reverse h) h)
       (iterate process-history)
       (take-while not-zero?)
       (map first)))

(defn extrapolated-values [data forward?]
  (->> data
       (mapcat (partial predict forward?))
       (reduce +)))

(def part-1 (extrapolated-values data true))
;; => 1930746032

(def part-2 (extrapolated-values data false))
;; => 1154
