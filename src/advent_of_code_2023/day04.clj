(ns advent-of-code-2023.day04
  (:require [common :refer [read-data]]
            [clojure.set :as set]))

(defn parse-line [l]
  (->> (re-seq #"\d+|\|" l)
       (rest)
       (split-with (complement #{"|"}))
       (map set)
       (apply set/intersection)
       (count)))

;; counts of winning cards
(def data (mapv parse-line (read-data 2023 4)))

;; power of 2
(defn points [winning] (bit-shift-left 1 (dec winning)))

(defn total-points [data]
  (->> data
       (remove zero?)
       (map points)
       (reduce +)))

(def part-1 (total-points data))
;; => 26443

(defn add-copies [buff rng copies]
  (reduce (fn [b id] (update b (inc id) + copies)) buff rng))

(defn copies [data]
  (->> (reduce (fn [buff id]                 
                 (add-copies buff
                             (range id (+ (data id) id)) ;; which to update
                             (buff id))) ;; current state of a game, number of copies till now
               (-> data count (repeat 1) vec) ;; init copies
               (-> data count range)) ;; process one by one
       (reduce +)))

(def part-2 (copies data))
;; => 6284877
