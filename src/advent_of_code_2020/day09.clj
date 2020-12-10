(ns advent-of-code-2020.day09
  (:require [common :refer [read-data]]))

(def data (map read-string (read-data 2020 9)))

(defn not-in-sums?
  [part]
  (let [p (butlast part)
        l (last part)
        s (set (for [x p y p :when (not= x y)] (+ x y)))]
    [l (not (s l))]))

(defn xmas-invalid?
  [data ^long preamble]
  (->> data
       (partition (inc preamble) 1)
       (map not-in-sums?)
       (filter second)
       (ffirst)))

(def part-1 (time (xmas-invalid? data 25)))
;; => 90433990

(defn iterate-data
  [data target curr id]
  (let [nx (map (fn [[sum mn mx] nxt] ;; structure is: current sum, min, max and sum of min and max
                  (let [nmn (min mn nxt)
                        nmx (max mx nxt)]
                    [(+ sum nxt) nmn nmx  (+ nmn nmx)])) curr (drop id data))]
    (if-let [s (first (filter #(= target (first %)) nx))]
      (reduced (last s))
      nx)))

(defn find-weakness
  [data target]
  (reduce (partial iterate-data data target)
          (map (partial repeat 4) data) ;; initialize with single number
          (range 1 (count data)))) ;; how many to drop?

(def part-2 (time (find-weakness data part-1)))
;; => 11691646
