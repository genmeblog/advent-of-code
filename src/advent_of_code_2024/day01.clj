(ns advent-of-code-2024.day01
  (:require [common :refer [read-data]]))

(defn parse-line [l]
  (map parse-long (re-seq #"\d+" l)))

(defn build-lists
  [data]
  (->> data
       (map parse-line)
       (apply map vector)
       (map sort)))

(def data (build-lists (read-data 2024 01)))

(defn distance
  [[left right]]
  (->> (map (comp abs -) left right)
       (reduce +)))

(def part-1 (distance data))
;; => 1765812

(defn similarity
  [[left right]]
  (let [freqs (frequencies right)]
    (transduce (map (fn [v] (* v (freqs v 0)))) + left)))

(def part-2 (similarity data))
;; => 20520794

