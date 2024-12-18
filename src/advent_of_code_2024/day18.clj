(ns advent-of-code-2024.day18
  (:require [common :refer [read-data get-numbers bfs->path-count neighbours4]]))

(def data (mapv get-numbers (read-data 2024 18)))

(defn empty-map [size] (vec (repeat size (vec (repeat size \.)))))

(defn build-map [data size steps]
  (reduce (fn [m pos] (assoc-in m pos \#)) (empty-map size) (take steps data)))

(defn around [m pos] (filter #(= \. (get-in m %)) (neighbours4 pos)))

(defn traverse [data size cnt]
  (let [d (build-map data (inc size) cnt)]
    (bfs->path-count (partial around d) [0 0] [size size])))

(def part-1 (traverse data 70 1024))
;; => 292

(defn find-blocking [data size cnt]
  (reduce (fn [m [x y :as pos]]
            (let [nm (assoc-in m pos \#)]
              (if (bfs->path-count (partial around nm) [0 0] [size size])
                nm
                (reduced (str x "," y))))) (build-map data (inc size) cnt) (drop cnt data)))

(def part-2 (find-blocking data 70 1024))
;; => "58,44"
