(ns advent-of-code-2017.day05
  (:require [common :refer [read-data parse]]))

(def data (parse (read-data 2017 5)))

(defn update-1
  [data id _]
  (update data id inc))

(defn iterator
  ([update-fn data] (iterator update-fn data 0 0))
  ([update-fn data ^long id ^long cnt]
   (if (or (neg? id) (>= id (count data)))
     cnt
     (let [^long jump (data id)]
       (recur update-fn (update-fn data id jump) (+ id jump) (inc cnt))))))

(def part-1 (iterator update-1 data))
;; => 315613

(defn update-2
  [data id ^long jump]
  (update data id (if (< jump 3) inc dec)))

(defonce part-2 (iterator update-2 data))
;; => 22570529
