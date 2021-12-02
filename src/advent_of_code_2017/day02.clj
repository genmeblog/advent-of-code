(ns advent-of-code-2017.day02
  (:require [common :refer [read-data parse]]
            [clojure.string :as str]))

(def data (->> (read-data 2017 2)
               (map #(str/split % #"\t"))
               (map parse)
               (map sort)))

(defn checksum
  [data]
  (->> data
       (map #(- (last %) (first %)))
       (reduce +)))

(def part-1 (checksum data))
;; => 32020

(defn row-divisibility
  [row]
  (for [a row b row
        :when (and (not= a b) (not (ratio? (/ a b))))]
    (/ a b)))

(defn divisibility
  [data]
  (->> data
       (mapcat row-divisibility)
       (reduce +)))

(def part-2 (divisibility data))
;; => 236



