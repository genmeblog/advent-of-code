(ns advent-of-code-2021.day06
  (:require [common :refer [read-single-line parse]]
            [clojure.string :as str]))

(def data (->> (str/split (read-single-line 2021 6) #",")
               (parse))) ;; adjusting for calculations

(def spawns (memoize (fn [days off]
                       (->> (range (- days off) 0 -7)
                            (map #(spawns % 9))
                            (reduce + 1)))))

(defn lanternfish-count
  [data days]
  (->> data
       (map (partial spawns days))
       (reduce +)))

(def part-1 (lanternfish-count data 80))
;; => 394994

(def part-2 (lanternfish-count data 256))
;; => 1765974267455
