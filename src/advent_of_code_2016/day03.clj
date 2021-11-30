(ns advent-of-code-2016.day03
  (:require [common :refer [read-data]]
            [clojure.string :as str]))

(def data (->> (read-data 2016 3)
               (map str/trim)
               (map #(str/split % #"[ ]+"))
               (map #(map read-string %))))

(defn triangle?
  [[x y z]]
  (and (> (+ x y) z)
       (> (+ x z) y)
       (> (+ y z) x)))

(def part-1 (count (filter triangle? data)))
;; => 917

(defn reorder-and-count
  [data]
  (->> (partition 3 3 data)
       (mapcat #(apply map vector %))
       (filter triangle?)
       (count)))

(def part-2 (reorder-and-count data))
;; => 1649
