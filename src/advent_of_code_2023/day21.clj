(ns advent-of-code-2023.day21
  (:require [common :refer [read-data]]
            [fastmath.core :as m]))

(defn parse [data]
  (let [c (count data)
        mid (quot c 2)]
    {:m (vec data) :size c :start [mid mid]}))

(def data (parse (read-data 2023 21)))
(def data1 (parse (read-data "examples" 2023 21)))

(defn inside? [size [x y]] (and (<= 0 x) (< x size) (<= 0 y) (< y size)))
(defn add [[x y] [a b]] [(+ x a) (+ y b)])
(defn posm [[x y] c] [(mod x c) (mod y c)])

(defn neighbours [m pos size]
  (->> [[0 1] [1 0] [0 -1] [-1 0]]
       (map (partial add pos))
       (filter (partial inside? size))
       (remove #(= \# (get-in m %)))))

(defn neighbours2 [m pos size]
  (->> [[0 1] [1 0] [0 -1] [-1 0]]
       (map (partial add pos))
       (remove #(= \# (get-in m (posm % size))))))

(defn traverser [nsf m end size]
  (let [f (fn [mem depth pos]
            (if (zero? depth)
              (= pos end)
              (some (partial mem mem (dec depth)) (nsf m pos size))))
        mf (memoize f)]
    (partial mf mf)))

(defn reach-count [{:keys [m start size]} nsf max-steps]
  (let [t (traverser nsf m start size)]
    (->> (for [x (range size)
               y (range size)
               :let [target [x y]]
               :when (not= \# (get-in m (posm target size)))]
           target)
         (reduce (fn [cnt target]
                   (if (t max-steps target) (inc cnt) cnt)) 0))))

(reach-count data1 neighbours2 10)
