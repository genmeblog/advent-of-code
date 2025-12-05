(ns advent-of-code-2025.day04
  (:require [common :refer [read-data neighbours8]]))

(defn parse [data]
  (let [d (mapv vec data)]
    {:rows (count d)
     :cols (count (first d))
     :data d}))

(defn count-rolls
  [data pos]
  (->> (neighbours8 pos)
       (map (partial get-in data))
       (filter #{\@})
       (count)))

(defn accessible
  [{:keys [rows cols data]}]
  (for [r (range rows)
        c (range cols)
        :let [pos [r c]]
        :when (and (= \@ (get-in data pos))
                   (< (count-rolls data [r c]) 4))]
    pos))

(defn remove-roll [data pos] (assoc-in data pos \.))
(defn remove-rolls
  [poss data]
  (reduce remove-roll data poss))

(defn step
  [data-map]
  (let [poss (accessible data-map)]
    (-> data-map
        (update :data (partial remove-rolls poss))
        (assoc :removed (count poss)))))

(defn count-all
  [data]
  (->> (parse data)
       (iterate step)
       (rest)
       (map :removed)
       (take-while pos?)))

(def data (count-all (read-data 2025 4)))

(def part-1 (first data))
;; => 1363

(def part-2 (reduce + data))
;; => 8184

