(ns advent-of-code-2021.day08
  (:require [common :refer [read-data split-line]]
            [clojure.set :as set]))

(defn split-and-pack
  [line]
  (zipmap [:digits :output] (map #(map set (split-line %)) (split-line line #" \| "))))

(def data (->> (read-data 2021 8)
               (map split-and-pack)))

(defn easy-numbers
  [data]
  (->> data
       (mapcat :output)
       (map count)
       (filter #{7 2 4 3})
       (count)))

(def part-1 (easy-numbers data))
;; => 493

(defn filter-cnt
  [cnt xs]
  (->> xs
       (filter #(= cnt (count (second %))))
       (ffirst)))

(defn differ
  [guesses known common]
  (->> guesses
       (map #(vector % (set/difference % known)))
       (filter-cnt common)))

(defn read-digits
  [{:keys [digits output]}]
  (let [counts (update-vals (group-by count digits) set)
        one (first (counts 2))
        seven (first (counts 3))
        eight (first (counts 7))
        four (first (counts 4))
        nine (differ (counts 6) four 2)
        six (differ (counts 6) seven 4)
        zero (first (disj (counts 6) nine six))
        two (differ (counts 5) nine 1)
        three (differ (counts 5) two 1)
        five (differ (counts 5) two 2)
        segments (zipmap [zero one two three four five six seven eight nine] "0123456789")]
    (Integer/parseInt (->> output
                           (map segments)
                           (apply str)) 10)))

(def part-2 (reduce + (map read-digits data)))
;; => 1010460
