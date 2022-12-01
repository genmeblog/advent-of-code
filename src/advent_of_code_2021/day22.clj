(ns advent-of-code-2021.day22
  (:require [common :refer [read-data parse]]
            [fastmath.core :as m]
            [clojure.set :as set]))

(def parser (partial parse #"(\w+)\sx=(\-?\d+)\.\.(\-?\d+),y=(\-?\d+)\.\.(\-?\d+),z=(\-?\d+)\.\.(\-?\d+)"))

(def data (map parser (read-data 2021 22)))


(count (reduce (fn [s [op x1 x2 y1 y2 z1 z2]]
                 (if (> (m/abs x1) 50)
                   s
                   (let [p (set (for [x (range x1 (inc x2))
                                      y (range y1 (inc y2))
                                      z (range z1 (inc z2))]
                                  [x y z]))]
                     (if (= op 'on)
                       (set/union s p)
                       (set/difference s p))))) #{} data))
;; => 587097


(defn small-cubes
  [data]
  (update-vals (reduce (fn [m [_ x1 x2 y1 y2 z1 z2]]
                         (-> (update m :x conj x1 x2)
                             (update :y conj y1 y2)
                             (update :z conj z1 z2))) {:x nil :y nil :z nil} data)
               (fn [lst] (let [sorted (distinct (sort lst))]
                          {:positions (into {} (map-indexed #(vector %2 %1)) sorted)
                           :sizes (into {} (map-indexed #(vector %1 (- (second %2) (first %2))) (partition 2 1 sorted)))}))))

(defn on-off
  [dict buff [op x1 x2 y1 y2 z1 z2]]
  ((case op on set/union off set/difference)
   buff
   (set (for [x (vals (select-keys (get-in dict [:x :positions]) (range x1 (inc x2))))
              y (vals (select-keys (get-in dict [:y :positions]) (range y1 (inc y2))))
              z (vals (select-keys (get-in dict [:z :positions]) (range z1 (inc z2))))]
          [x y z]))))

#_(def res (reduce (partial on-off (small-cubes data)) #{} data))

(comment
  (on-off (small-cubes data) #{} (first data))

  (small-cubes data)

  (take 2 data)
  ;; => ([on -5 46 -32 20 -18 26] [on -47 -1 -28 19 -49 3])


  (count (distinct (concat (map #(nth % 6) data) (map #(nth % 5) data)))))



