(ns advent-of-code-2020.day06
  (:require [advent-of-code-2020.common :refer [read-data-as-blocks]]
            [clojure.string :as str]))

(def data (read-data-as-blocks 6))

(defn total-yes-answers
  [data]
  (->> data
       (map (comp set (partial str/join "")))
       (map count)
       (reduce +)))

(def part-1 (total-yes-answers data))
;; => 6530

(defn common-group-yes-answers
  [g]
  (let [c (count g)]
    (->> (str/join "" g)
         (frequencies)
         (vals)
         (filter #(= % c))
         (count))))

(defn total-common-yes-answers
  [data]
  (->> data
       (map common-group-yes-answers)
       (reduce +)))

(def part-2 (total-common-yes-answers data))
;; => 3323
