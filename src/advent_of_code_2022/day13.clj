(ns advent-of-code-2022.day13
  (:require [common :refer [read-data-as-blocks]]
            [clojure.edn :as edn]))

(defn parse [tuple] (mapv edn/read-string tuple))

(def data (map parse (read-data-as-blocks 2022 13)))

(defn compare-pairs
  [[fa & ra] [fb & rb]]
  (let [res (cond
              (or (not fa) (not fb) 
                  (and (number? fa) (number? fb))) (compare fa fb)
              (number? fa)  (compare-pairs [fa] fb)
              (number? fb) (compare-pairs fa [fb])
              (and (vector? fa) (vector? fa)) (compare-pairs fa fb)
              :else 0)]
    (if (and (zero? res) (or ra rb)) (recur ra rb) res)))

(defn ordered-pairs-score
  [data]
  (->> data
       (map-indexed (fn [idx [s1 s2]] (vector (inc idx) (compare-pairs s1 s2))))
       (filter (comp neg? second))
       (map first)
       (reduce +)))

(def part-1 (ordered-pairs-score data))
;; => 5852

(def divider-packets #{[[2]] [[6]]})

(defn decoder-key
  [data]
  (->> (conj data divider-packets)
       (mapcat identity) 
       (sort compare-pairs)
       (keep-indexed (fn [id v] (when (divider-packets v) (inc id))))
       (reduce *)))

(def part-2 (decoder-key data))
;; => 24190
