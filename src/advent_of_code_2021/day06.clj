(ns advent-of-code-2021.day06
  (:require [common :refer [read-single-line parse]]
            [clojure.string :as str]))

(def data (->> (str/split (read-single-line 2021 6) #",")
               (parse)
               (map inc))) ;; adjusting for calculations

(def spawns (memoize (fn [days off]
                       (if (<= days off)
                         0
                         (let [cnt (inc (quot (- days (inc off)) 7))] ;; how many spawns
                           (->> (range cnt)
                                (map #(spawns (- days (* 7 %) off) 9))
                                (reduce + cnt)))))))

(defn lanternfish-count
  [data days]
  (->> data
       (map (partial spawns (inc days)))
       (reduce + (count data))))

(def part-1 (lanternfish-count data 80))
;; => 394994

(def part-2 (lanternfish-count data 256))
;; => 1765974267455

