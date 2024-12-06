(ns advent-of-code-2024.day05
  (:require [common :refer [read-data-as-blocks get-numbers]]))

(defn ->comparator [pairs]
  (let [positives (set (map get-numbers pairs))]
    (fn [x y] (cond
               (== x y) 0
               (positives [x y]) -1
               :else 1))))

(defn middle [l] (l (int (/ (count l) 2))))

(defn validate-and-middle [comparator l]
  (let [l2 (vec (sort comparator l))]
    [(= l l2) (middle l2)]))

(defn sort-lists [comparator lists]
  (->> lists
       (map get-numbers)
       (map (partial validate-and-middle comparator))))

(defn parse-data [[pairs lists]]
  (sort-lists (->comparator pairs) lists))

(def data (parse-data (read-data-as-blocks 2024 05)))

(defn sum-of-middles [data valid?]
  (transduce (comp (filter (comp valid? first)) (map second)) + data))

(def part-1 (sum-of-middles data true?))
;; => 4790

(def part-2 (sum-of-middles data false?))
;; => 6319

