(ns advent-of-code-2024.day10
  (:require [common :refer [read-data]]))

(defn parse-line [line] (mapv (comp parse-long str) line))

(def data (mapv parse-line (read-data 2024 10)))

(defn find-zeros [data]
  (let [s (range (count data))]
    (for [row s col s :let [v (get-in data [row col])] :when (and v (zero? v))]
      [row col])))

(defn surroundings [data v [row col]]
  (filter #(= v (get-in data %)) [[(inc row) col] [(dec row) col]
                                  [row (inc col)] [row (dec col)]]))

(defn traverse-one
  ([data method start] (traverse-one data method 0 [start]))
  ([data method curr poss]
   (if (= curr 9)
     (count poss)
     (recur data method (inc curr) (method (mapcat (partial surroundings data (inc curr)) poss))))))

(defn traverse [data method]
  (transduce (map (partial traverse-one data method)) + (find-zeros data)))

(def part-1 (traverse data distinct))
;; => 638

(def part-2 (traverse data identity))
;; => 1289
