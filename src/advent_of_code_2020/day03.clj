(ns advent-of-code-2020.day03
  (:require [common :refer [read-data]]))

(def data (read-data 2020 3))

(defn tree-count
  [data [right down]]
  (let [len (count (first data))]
    (->> data
         (take-nth down)
         (map-indexed (fn [idx line]
                        (nth line (mod (* right idx) len))))
         (filter #{\#})
         (count))))

(def part-1 (time (tree-count data [3 1])))
;; => 151

;;

(def slopes [[1 1] [3 1] [5 1] [7 1] [1 2]])

(defn multiply-trees
  [data slopes]
  (->> slopes
       (map (partial tree-count data))
       (reduce *)))

(def part-2 (time (multiply-trees data slopes)))
;; => 7540141059
