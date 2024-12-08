(ns advent-of-code-2024.day08
  (:require [common :refer [read-data addv subv inside?]]))

(defn pairs [ps]  (for [p1 ps p2 ps :when (not= p1 p2)] [p1 p2]))

(defn positions [data]
  {:size (count data)
   :pps (->> (for [[rowid row] (map-indexed vector data)
                   [colid antenna] (map-indexed vector row)
                   :when (not= \. antenna)]
               [antenna [rowid colid]])
             (reduce (fn [buff [antenna pos]] (update buff antenna conj pos)) {})
             (vals)
             (map pairs))})

(def data (positions (read-data 2024 8)))

(defn antinodes [size ps]
  (->> (map (fn [[p1 p2]] (addv (subv p2 p1) p2)) ps)
       (filter (partial inside? size))))

(defn resonants [size ps]
  (mapcat (fn [[p1 p2]] (->> (iterate (partial addv (subv p2 p1)) p2)
                            (take-while (partial inside? size)))) ps))

(defn all-antinodes [method {:keys [pps size]}]
  (->> (mapcat (partial method size) pps) set count))

(def part-1 (all-antinodes antinodes data))
;; => 320

(def part-2 (all-antinodes resonants data))
;; => 1157
