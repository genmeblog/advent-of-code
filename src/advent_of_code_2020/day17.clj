(ns advent-of-code-2020.day17
  (:require [common :refer [read-data]]
            [fastmath.vector :as v]))

(defn parse
  [data]
  (set (for [[y line] (map-indexed vector data)
             [x c] (map-indexed vector line)
             :when (= c \#)]
         (v/vec4 x y 0 0))))

(def data (parse (read-data 2020 17)))

(defn active?
  [state pos neighbours]
  (let [nbs (reduce (fn [cnt v]
                      (+ cnt (if (state (v/add pos v)) 1 0))) 0 neighbours)
        active-pos? (state pos)]
    (or (and active-pos? (or (== 2 nbs) (== 3 nbs)))
        (and (not active-pos?) (== 3 nbs)))))

(def all-neighbours
  (vec (let [r [-1 0 1]]
         (for [x r y r z r w r
               :when (not-every? zero? [x y z w])]
           (v/vec4 x y z w)))))

(defn step
  [w? state]
  (let [mn (v/add (reduce v/emn state) (v/vec4 -1 -1 -1 -1))
        mx (v/add (reduce v/emx state) (v/vec4 2 2 2 2))]
    (set (for [x (range (mn 0) (mx 0))
               y (range (mn 1) (mx 1))
               z (range (mn 2) (mx 2))
               w (if w? (range (mn 3) (mx 3)) [0])
               :let [pos (v/vec4 x y z w)]
               :when (active? state pos all-neighbours)]
           pos))))

(defn cubes
  [data w?]
  (-> (partial step w?)
      (iterate data)
      (nth 6)
      (count)))

(def part-1 (time (cubes data false)))
;; => 269

(def part-2 (time (cubes data true)))
;; => 1380
