(ns advent-of-code-2023.day06
  (:require [fastmath.solver :as solve]
            [fastmath.core :as m]
            [common :refer [read-data]]))

(defn parse-data [data] (map (partial re-seq #"\d+") data))

(def data (parse-data (read-data 2023 6)))

(defn solve-pair [[time distance]]
  (->> (solve/quadratic -1 time (- (m/next-double distance)))
       (reverse)
       (map int)
       (apply -)))

(defn solve-races [data]
  (->> data
       (map (partial map parse-long))
       (apply map vector)
       (map solve-pair)
       (reduce *)))

(def part-1 (solve-races data))
;; => 1413720

(defn solve-race [data]
  (->> data
       (map (comp parse-long (partial apply str)))
       (solve-pair)))

(def part-2 (solve-race data))
;; => 30565288
