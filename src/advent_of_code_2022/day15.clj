(ns advent-of-code-2022.day15
  (:require [common :refer [read-data]]
            [clojure2d.core :as c2d]
            [fastmath.vector :as v]
            [clojure2d.color :as c]
            [clojure2d.extra.utils :as utils]))

(defn parse [line]
  (let [[sx sy bx by] (map parse-long (re-seq #"[-]*\d+" line))]
    [sx sy (+ (abs (- sx bx)) (abs (- sy by))) bx by]))

(def data (map parse (read-data 2022 15)))

(defn slice
  [y [sx sy r]]
  (let [v (- r (abs (- sy y)))]
    (when (pos? v) [(- sx v) (+ sx v)])))

(defn segments
  [data y]
  (let [z (->> data
               (map (partial slice y))
               (filter identity)
               (sort-by first))]
    (reduce (fn [[[x1 y1] & r :as all] [x2 y2 :as i2]]
              (if (<= x2 y1)
                (conj r [x1 (max y1 y2)])
                (conj all i2))) (list (first z)) (rest z))))

(defn not-containing
  [data y]
  (->> (segments data y)
       (map (comp inc - (partial apply -)))
       (reduce +)
       (dec)))

(def part-1 (not-containing data 2000000))
;; => 4737443

(defn tuning-frequency
  [data rng]
  (-> (for [y (range (inc rng))
            :let [[[right _] [_ left]] (segments data y)]
            :when (and left (= 2 (- right left)))]
        (+ (* 4000000 (inc left)) y))
      (first)))

(defonce part-2 (tuning-frequency data 4000000))
;; => 11482462818989

;;

(c2d/with-canvas [c (c2d/canvas 900 800)]
  (c2d/set-background c 0)
  (c2d/translate c 250 250)
  (c2d/set-color c :green)
  (c2d/line c -250 200 650 200)
  (c2d/set-color c :lightblue 200)
  (c2d/rect c 0 0 400 400 true)
  (doseq [[x y r bx by] data]
    (c2d/set-color c :white)
    (c2d/crect c (/ x 10000.0) (/ y 10000.0) 3 3)
    (c2d/set-color c :red)
    (c2d/crect c (/ bx 10000.0) (/ by 10000.0) 10 10)
    (c2d/set-color c "#aa" 100)
    (c2d/filled-with-stroke c (c/gray 0xaa 100) (c/gray 0xbb 150) 
                            c2d/path (map #(v/div % 10000.0) [[(- x r) y]
                                                              [x (- y r)]
                                                              [(+ x r) y]
                                                              [x (+ y r)]]) true))
  (c2d/save c "images/advent_of_code_2022/day15.jpg")
  (utils/show-image c))

