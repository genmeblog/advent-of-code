(ns advent-of-code-2025.day08
  (:require [common :refer [read-data prod get-numbers unique-pairs]]
            [clojure.set :as set]))

(defn sq ^long [^long x] (* x x))
(defn with-dist
  [[[^long x1 ^long y1 ^long z1 :as p1] [^long x2 ^long y2 ^long z2 :as p2]]]
  [(+ (sq (- x1 x2)) (sq (- y1 y2)) (sq (- z1 z2))) #{p1 p2}])

(defn order-boxes
  [data]
  (let [data (map get-numbers data)]
    {:ordered (->> (unique-pairs data)
                   (set)
                   (map with-dist)
                   (sort-by first)
                   (map second))
     :size (count data)}))

(def data (order-boxes (read-data 2025 8)))

(defn process-circuits
  [buff curr-pair]
  (reduce (fn [[other-circuits pair] circuit]
            (if (empty? (set/intersection pair circuit))
              [(conj other-circuits circuit) pair]
              [other-circuits (set/union pair circuit)]))
          [[] curr-pair] buff))

(defn process-pairs
  [size pairs]
  (reduce (fn [[buff last-pair] curr-pair]
            (if (= (first (map count buff)) size)
              (reduced (prod (map first last-pair)))
              [(apply conj (process-circuits buff curr-pair)) curr-pair]))
          [[] nil] pairs))

(defn n-shortests
  [{:keys [ordered size]} cnt]
  (->> ordered
       (take cnt)
       (process-pairs size)
       (first)
       (map count)
       (sort >)
       (take 3)
       (prod)))

(def part-1 (n-shortests data 1000))
;; => 68112

(def part-2 (process-pairs (:size data) (:ordered data)))
;; => 44543856
