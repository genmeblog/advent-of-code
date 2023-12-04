(ns advent-of-code-2015.day03
  (:require [common :refer [read-single-line]]))

(def dimensions-data (read-single-line 2015 3))

(defn visited-houses
  [instr]
  (reduce (fn [[[x y :as current] :as visited] in]
            (conj visited (case in
                            \< [(dec x) y]
                            \^ [x (dec y)]
                            \v [x (inc y)]
                            \> [(inc x) y]
                            current))) '([0 0]) instr))

(defn visited-with-present
  [data]
  (->> (visited-houses data)
       (distinct)
       (count)))

(def part-1 (visited-with-present dimensions-data))
;; => 2572

(defn visited-with-robo
  [data]
  (->> data
       (partition 2)
       (apply map list)
       (mapcat visited-houses)
       (distinct)
       (count)))

(def part-2 (visited-with-robo dimensions-data))
;; => 2631
