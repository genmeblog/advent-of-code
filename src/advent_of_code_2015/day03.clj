(ns advent-of-code-2015.day03
  (:require [clojure.java.io :as io]
            [clojure.string :as s]))

(set! *unchecked-math* :warn-on-boxed)
(set! *warn-on-reflection* true)

(def dimensions-data (slurp (io/resource "day03.txt")))

(defn visited
  [instr]
  (reduce (fn [visited in]
            (let [[^int x ^int y :as current] (first visited)]
              (conj visited (case in
                              \< [(dec x) y]
                              \^ [x (dec y)]
                              \v [x (inc y)]
                              \> [(inc x) y]
                              current)))) '([0 0]) instr))

(defn visited-with-present
  [data]
  (->> data
       (visited)
       (distinct)
       (count)))

(def part-1 (visited-with-present dimensions-data))

(defn visited-with-robo
  [data]
  (->> data
       (partition 2)
       (apply map list)
       (mapcat visited)
       (distinct)
       (count)))

(def part-2 (visited-with-robo dimensions-data))
