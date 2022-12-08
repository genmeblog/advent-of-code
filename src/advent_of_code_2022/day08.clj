(ns advent-of-code-2022.day08
  (:require [common :refer [read-data]]))

(defn parse [line] (map (comp parse-long str) line))

(defn build-data
  [data]
  (let [parsed (mapv parse data)]
    {:horizontal parsed
     :vertical (apply mapv vector parsed)}))

(def data (build-data (read-data 2022 8)))

(defn visible? [f line] (if (every? (partial > f) line) 1 0))
(defn visible-trees [f line] (let [[a b] (split-with (partial > f) line)]
                            (+ (count a) (count (take 1 b)))))

(defn check-visibility
  [f agr {:keys [horizontal vertical]}]
  (for [row-id (range (count horizontal))
        col-id (range (count vertical))
        :let [[r1 [t1 & r2]] (split-at col-id (horizontal row-id))
              [c1 [t2 & c2]] (split-at row-id (vertical col-id))
              res (agr (f t1 (reverse r1))
                       (f t1 r2)
                       (f t2 (reverse c1))
                       (f t2 c2))]
        :when (pos? res)]
    res))

(def part-1 (count (check-visibility visible? + data)))
;; => 1681

(def part-2 (reduce max (check-visibility visible-trees * data)))
;; => 201684
