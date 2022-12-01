(ns advent-of-code-2019.day06
  (:require [common :refer [read-data split-line]]
            [clojure.set :as set]))

(defn path
  [data k]
  (when-let [dk (data k)]
    (conj (path data dk) k)))

(defn orbits
  [data]
  (into {} (map #(vector % (path data %)) (keys data))))

(defn parse
  [input]
  (->> input
       (map #(-> % (split-line #"\)") reverse vec))
       (into {})
       (orbits)))

(def data (parse (read-data 2019 6)))

(defn all-paths
  [data]
  (->> data vals (map count) (reduce +)))

(def part-1 (all-paths data))
;; => 135690

(defn minimal-path
  [data]
  (let [you (set (data "YOU"))
        san (set (data "SAN"))
        x (set/intersection you san)]
    (- (count (set/union (set/difference you x)
                         (set/difference san x))) 2)))

(def part-2 (minimal-path data))
;; => 298
