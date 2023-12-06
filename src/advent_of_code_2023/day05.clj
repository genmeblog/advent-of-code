(ns advent-of-code-2023.day05
  (:require [common :refer [read-data-as-blocks]]
            [clojure.string :as str]
            [fastmath.vector :as v]))

(defn parse-rule [rule]
  (let [[t s l] (map parse-long (str/split rule #"\s+"))]
    [s (+ s l -1) (- t s)])) ;; start, end, shift

(defn parse-block [[header & rules]]
  (let [[_ source target] (re-find #"^(\w+)\-to\-(\w+)" header)]
    [source [target (map parse-rule rules)]]))

(defn parse-data [blocks]
  {:seeds (->> (re-seq #"\d+" (ffirst blocks)) (map parse-long))
   :rules (->> (map parse-block (rest blocks)) (into {}))})

(def data (parse-data (read-data-as-blocks 2023 5)))

(defn intersection-and-differences
  [[a b :as i] [c d shift]]
  (if (and (<= a d) (>= b c)) ;; overlap?
    [(v/shift [(max a c) (min b d)] shift)
     (cond
       (and (> c a) (< d b)) [[a (dec c)] [(inc d) b]] ;; inside
       (and (<= c a) (< d b)) [[(inc d) b]] ;; left
       (and (>= d b) (> c a)) [[a (dec c)]])] ;; right
    [nil [i]])) ;; non-overlapping

(defn pass-ranges-through-targets
  [sources targets]
  (->> targets
       (reduce (fn [[moved seeds] target]
                 (loop [moved moved
                        seeds seeds
                        leftovers []]
                   (if-let [seed (first seeds)]
                     (let [[p rst] (intersection-and-differences seed target)]
                       (recur (if p (conj moved p) moved)
                              (rest seeds)
                              (concat leftovers rst)))
                     [moved leftovers]))) [[] sources])
       (apply concat)))

(defn process-rule [rules [what ranges]]
  (if (= "location" what)
    (->> ranges flatten (apply min) int)
    (let [[target rules-list] (rules what)]
      (recur rules [target (pass-ranges-through-targets ranges rules-list)]))))

(defn seeds-ids [seeds] (map (partial repeat 2) seeds))
(defn seeds-ranges [seeds] (map (fn [[a b]] [a (+ a b -1)])(partition 2 seeds)))

(defn find-location [data seed-fn]
  (process-rule (:rules data) ["seed" (seed-fn (:seeds data))]))

(def part-1 (find-location data seeds-ids))
;; => 806029445

(def part-2 (find-location data seeds-ranges))
;; => 59370572
