(ns advent-of-code-2023.day05
  (:require [common :refer [read-data-as-blocks]]
            [clojure.string :as str]
            [fastmath.vector :as v]))

(defn parse-rule [rule]
  (let [[t s l] (map parse-long (str/split rule #"\s+"))]
    [s (+ s l -1) (- t s)])) ;; start, end, shift

(defn parse-block [[header & rules]]
  (let [[_ source target] (re-find #"^(\w+)\-to\-(\w+)" header)]
    [source [target (sort (map parse-rule rules))]]))

(defn parse-data [blocks]
  [(->> (re-seq #"\d+" (ffirst blocks)) (map parse-long)) ;; seeds
   (->> (map parse-block (rest blocks)) (into {}))]) ;; rules

(def data (parse-data (read-data-as-blocks 2023 5)))

(defn split-range-and-shift [[a b :as i] [c d shift]]
  (cond
    (and (< a c) (< d b)) [[a (dec c)] (v/shift [c d] shift) [(inc d) b]] ;; inside
    (and (< a c) (<= c b) (<= b d)) [[a (dec c)] (v/shift [c b] shift)] ;; right overlap
    (and (<= c a) (<= a d) (< d b)) [(v/shift [a d] shift) [(inc d) b]] ;; left overlap
    (and (<= c a) (<= b d)) [(v/shift i shift)] ;; covering
    :else [i])) ;; outside

(defn split-by-rules [rules rng]
  (reduce (fn [[[_ b :as curr] & rst] [_ d :as t]]
            (let [nres (apply conj rst (split-range-and-shift curr t))]
              (if (>= d b) ;; other rules are outside our target (rules are sorted ascending)
                (reduced nres)
                nres))) (list rng) rules))

(defn process-rule [rules [what ranges]]
  (if (= "location" what)
    (->> ranges flatten (apply min) int)
    (let [[target rules-list] (rules what)]
      (recur rules [target (mapcat (partial split-by-rules rules-list) ranges)]))))

(defn seeds-ids [seeds] (map (partial repeat 2) seeds))
(defn seeds-ranges [seeds] (map (fn [[a b]] [a (+ a b -1)])(partition 2 seeds)))

(defn find-location [[seeds rules] seed-fn]
  (process-rule rules ["seed" (seed-fn seeds)]))

(def part-1 (find-location data seeds-ids))
;; => 806029445

(def part-2 (find-location data seeds-ranges))
;; => 59370572
