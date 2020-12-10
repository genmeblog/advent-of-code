(ns advent-of-code-2019.day04
  (:require [clojure.set :as s]))

(set! *unchecked-math* :warn-on-boxed)
(set! *warn-on-reflection* true)

(def data (map str (range 245182 790572)))

(defn check-multiples [pds] (set (map first (filter (partial apply =) pds))))
(defn not-decreasing? [pds] (every? (fn [[a b]] (<= (int a) (int b))) pds))
(defn meets-criteria?
  [in]
  (let [d (partition 2 1 in)]
    (and (not-decreasing? d) (seq (check-multiples d)))))

(def part-1 (count (filter meets-criteria? data)))

(defn meets-criteria-no-blocks?
  [in]
  (let [d (partition 2 1 in)]
    (and (not-decreasing? d) (seq (s/difference (check-multiples d)
                                                (check-multiples (partition 3 1 in)))))))

(def part-2 (count (filter meets-criteria-no-blocks? data)))
