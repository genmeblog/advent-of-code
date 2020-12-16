(ns advent-of-code-2018.day02
  (:require [common :refer [read-data]]))

(def data (read-data 2018 2))

;; frequencies as list of sets
(defn freqs
  [data]
  (map (comp set keys (partial group-by second) frequencies) data))

(defn how-many
  "How many sets with given value"
  [f val]
  (count (filter #(% val) f)))

(defn checksum
  [data]
  (let [f (freqs data)]
    (* (how-many f 2) (how-many f 3))))

(def part-1 (checksum data))
;; => 7808

;; part 2

(defn str-diff
  "Difference between two strings. Returns 0 when strings differ by exactly one character."
  [a b]
  (->> (map #(not= %1 %2) a b)
       (filter identity)
       (count)
       (dec)))

(defn correct-boxes
  [data]
  (for [a data b data
        :when (zero? (str-diff a b))]
    [a b]))

(defn common-letters
  [data]
  (let [[a b] (first (correct-boxes data))]
    (reduce (fn [r [l1 l2]]
              (if (= l1 l2) (str r l1) r)) "" (map vector a b))))

(def part-2 (common-letters data))
;; => "efmyhuckqldtwjyvisipargno"
