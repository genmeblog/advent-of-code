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

(def vmn (v/vec4 -1 -1 -1 -1))
(def vmx (v/vec4 2 2 2 2))

(defn ncount
  ^long [state pos neighbours]
  (reduce (fn [^long cnt v] (+ cnt (if (state (v/add pos v)) 1 0))) 0 neighbours))

(defn active?
  [state pos neighbours]
  (let [nbs (ncount state pos neighbours)
        active-pos? (state pos)]
    (or (and active-pos? (or (== 2 nbs) (== 3 nbs)))
        (and (not active-pos?) (== 3 nbs)))))

(def all-neighbours
  (let [r (range -1 2)]
    (for [x r y r z r w r
          :when (not (every? zero? [x y z w]))]
      (v/vec4 x y z w))))

(defn step
  [w? ns state]
  (let [mn (v/add vmn (reduce v/emn state))
        mx (v/add vmx (reduce v/emx state))]
    (set (for [x (range (mn 0) (mx 0))
               y (range (mn 1) (mx 1))
               z (range (mn 2) (mx 2))
               w (if w? (range (mn 3) (mx 3)) [0])
               :let [pos (v/vec4 x y z w)]
               :when (active? state pos ns)]
           pos))))

(defn cubes
  [data w?]
  (let [ns (if-not w? (filter #(zero? (% 3)) all-neighbours) all-neighbours)]
    (count (nth (iterate (partial step w? ns) data) 6))))

(def part-1 (time (cubes data false)))
;; => 269

(def part-2 (time (cubes data true)))
;; => 1380
