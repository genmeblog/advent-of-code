(ns advent-of-code-2025.day05
  (:require [common :refer [read-data-as-blocks]]))

(defn parse-range [r] (map parse-long (re-seq #"\d+" r)))

(defn parse
  [[ranges ids]]
  {:ranges (map parse-range ranges)
   :ids (map parse-long ids)})

(def data (parse (read-data-as-blocks 2025 5)))

(defn between? [x [a b]] (<= a x b))
(defn in-ranges? [ranges id] (some (partial between? id) ranges))

(defn fresh
  [{:keys [ranges ids]}]
  (->> ids (filter (partial in-ranges? ranges)) count))

(defn merge-range
  [[[a b] & rst :as curr] [na nb :as nrange]]
  (if (<= a na b)
    (conj rst [a (max b nb)])
    (conj curr nrange)))

(defn merge-ranges
  [ranges]
  (let [[fr & rs] (sort-by first ranges)]
    (reduce merge-range (list fr) rs)))

(defn all-fresh
  [{:keys [ranges]}]
  (->> (merge-ranges ranges)
       (reduce (fn [cnt [a b]] (+ 1 cnt (- b a))) 0)))

(def part-1 (fresh data))
;; => 739

(def part-2  (all-fresh data))
;; => 344486348901788

