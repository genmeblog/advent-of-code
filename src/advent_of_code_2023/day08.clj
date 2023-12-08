(ns advent-of-code-2023.day08
  (:require [common :refer [read-data]]
            [fastmath.core :as m]))

(defn parse-data [[direction _ & rules]]
  {:directions (map (fn [d] (if (= \L d) first second)) direction)
   :rules (->> (map (partial re-seq #"[A-Z\d]+") rules)
               (map (juxt first rest))
               (into {}))})

(def data (parse-data (read-data 2023 8)))

(defn travel [{:keys [directions rules]} starting-pos]
  (reductions (fn [curr dir] (dir (rules curr))) starting-pos (cycle directions)))

(defn travel-steps [starting-pos ending-pred data]
  (->> starting-pos
       (travel data)
       (take-while ending-pred)
       (count)))

(def travel-steps-AAA-ZZZ (partial travel-steps "AAA" (complement #{"ZZZ"})))

(def part-1 (travel-steps-AAA-ZZZ data))
;; => 22199

(defn ghost-steps [{:keys [rules] :as data}]
  (->> rules
       (keys)
       (filter (comp #{\A} last))
       (map (fn [starting-pos] (travel-steps starting-pos (comp (complement #{\Z}) last) data)))
       (reduce m/lcm)))

(def part-2 (ghost-steps data))
;; => 13334102464297
