(ns advent-of-code-2021.day15
  (:require [common :refer [read-data parse]]))

(defn parser
  ([data] (parser data 1))
  ([data span]
   (let [z (mapv (comp parse (partial map str)) data)
         c (count z)]
     {:end (->> (* span c) dec (repeat 2) vec)
      :data (into {} (for [x (range c) y (range c)
                           sx (range span) sy (range span)
                           :let [v (get-in z [x y])]]
                       [[(+ x (* sx c)) (+ y (* sy c))]
                        (-> v (+ sx sy) dec (rem 9) inc)]))
      :dist {[0 0] 0}})))

(def data (read-data 2021 15))

(defn dijkstra
  [{:keys [data dist end] :as current-data}]
  (let [[[x y :as min-pos] min-d] (apply min-key second dist)
        new-dist (->> [[(dec x) y] [(inc x) y] [x (dec y)] [x (inc y)]]
                      (select-keys data)
                      (reduce (fn [curr-dist [n nv]]
                                (assoc curr-dist n (min (+ min-d nv)
                                                        (get dist n Integer/MAX_VALUE)))) dist))]
    (or (new-dist end)
        (recur (-> current-data
                   (update :data dissoc min-pos)
                   (assoc :dist (dissoc new-dist min-pos)))))))

(def part-1 (dijkstra (parser data)))
;; => 553

(defonce part-2 (time (dijkstra (parser data 5))))
;; => 2858
