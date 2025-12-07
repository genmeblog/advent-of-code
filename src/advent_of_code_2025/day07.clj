(ns advent-of-code-2025.day07
  (:require [common :refer [read-data sum]]))

(defn parse-splitters
  [line]
  (->> line
       (map-indexed vector)
       (filter (comp #{\^} second))
       (map first)))

(defn calculate-line-splitters
  [[nbeams cnt :as state] id]
  (if-let [routes (nbeams id)]
    [(-> nbeams
         (dissoc id)
         (update (inc id) (fnil + 0) routes)
         (update (dec id) (fnil + 0) routes))
     (inc cnt)]
    state))

(defn calculate-line [[beams cnt] line]  (reduce calculate-line-splitters [beams cnt] line))
(defn calculate [splits beams] (reduce calculate-line [beams 0] splits))

(defn parse-and-calculate
  [lines]
  (calculate (->> lines (take-nth 2) rest (map parse-splitters))
             {(-> lines first count (quot 2)) 1}))

(def data (parse-and-calculate (read-data 2025 7)))

(def part-1 (-> data second))
;; => 1600

(def part-2 (-> data first vals sum))
;; => 8632253783011
