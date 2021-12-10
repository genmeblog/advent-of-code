(ns advent-of-code-2021.day09
  (:require [common :refer [read-data parse]]
            [clojure.string :as str]
            [clojure2d.core :as c2d]
            [clojure2d.extra.utils :as utils]
            [clojure2d.color :as c]))

(defn parser [data] (->> data
                         (map #(str/split % #""))
                         (mapv parse)))

(def data (parser (read-data 2021 9)))

(defn points-around [[x y]] [[(dec x) y] [(inc x) y] [x (dec y)] [x (inc y)]])

(defn make-basin
  [data]
  (let [basin-fn (memoize (fn [f [pos p :as curr]]
                            (if-let [np (->> (points-around pos)
                                             (remove #(<= p (get-in data % 10)))
                                             (first))]
                              (f f [np (get-in data np 10)])
                              curr)))]
    (partial basin-fn basin-fn)))

(defn basins
  [data]
  (->> (for [x (range (count (first data)))
             y (range (count data))
             :let [pos [y x]
                   v (get-in data pos 10)]
             :when (< v 9)]
         [pos v])
       (map (make-basin data))
       (frequencies)))

(defn risk-level
  [data]
  (->> (basins data)
       (keys)
       (map (comp inc second))
       (reduce +)))

(def part-1 (risk-level data))
;; => 585

(defn biggest-3
  [data]
  (->> (basins data)
       (vals)
       (sort >)
       (take 3)
       (reduce *)))

(def part-2 (biggest-3 data))
;; => 827904

(c2d/with-canvas [c (c2d/canvas 600 600)]
  (c2d/set-background c :navy)
  (let [pal (vec (c/palette [:red :orange :navy :blue] 10))]
    (doseq [x (range 100)
            y (range 100)
            :let [v (get+ data [y x])]]
      (c2d/set-color c (pal v))
      (c2d/rect c (+ 50 (* 5 y)) (+ 50 (* 5 x)) 5 5)))
  #_(c2d/save c "images/advent_of_code_2021/day08.jpg")
  (utils/show-image c))

(c2d/with-canvas [c (c2d/canvas 600 600)]
  (c2d/set-background c :navy)
  (let [basin (make-basin data)
        basin-color (memoize (fn [_] (c/random-color)))]
    (doseq [x (range 100)
            y (range 100)
            :let [v (get+ data [y x])]
            :when (< v 9)]
      (c2d/set-color c (basin-color (basin [[y x] v])))
      (c2d/rect c (+ 50 (* 5 y)) (+ 50 (* 5 x)) 5 5)))
  #_(c2d/save c "images/advent_of_code_2021/day08b.jpg")
  (utils/show-image c))

