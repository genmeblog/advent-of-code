(ns advent-of-code-2024.day20
  (:require [common :refer [read-data bfs->path neighbours4]]))

(defn parse [data]
  (let [d (mapv vec data)
        c (range (count data))]
    (->> (for [row c col c
               :let [v (get-in d [row col])]
               :when (#{\E \S} v)]
           [(if (= \E v) :end :start) [row col]])
         (into {:data d}))))

(def data (parse (read-data "examples" 2024 20)))

(defn around [m pos] (filter #(#{\. \S \E} (get-in m %)) (neighbours4 pos)))

(defn traverse [{:keys [data start end]}]
  (->> (bfs->path (partial around data) start end)
       (reverse)
       (map-indexed vector)))

(count (traverse data))
