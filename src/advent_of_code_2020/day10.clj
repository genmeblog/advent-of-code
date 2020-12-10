(ns advent-of-code-2020.day10
  (:require [common :refer [read-data]]))

(def data (sort > (conj (map read-string (read-data 2020 10)) 0)))

(defn jolts
  [data]
  (let [m (->> data
               (partition 2 1)
               (map (partial apply -))
               (frequencies))]
    (* (m 1) (inc (m 3)))))

(def part-1 (time (jolts data)))
;; => 2592

(defn pairs
  [data]
  (group-by first (for [x data y data
                        :when (<= 1 (- y x) 3)]
                    [x y])))

(defn arrangements
  [data]
  (let [prs (pairs data)]
    (get (reduce (fn [buff id] ;; buff acts as memoize
                   (->> (prs id)
                        (map (comp buff second))
                        (reduce +)
                        (max 1)
                        (assoc buff id))) {} data) 0)))

(def part-2 (time (arrangements (conj data 180))))
;; => 198428693313536
