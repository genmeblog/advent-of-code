(ns advent-of-code-2023.day18
  (:require [common :refer [read-data]]
            [clojure2d.core :as c2d]
            [clojure2d.extra.utils :as utils]))

(defn parse-line [l]
  (let [[d s color] (re-seq #"\w+|\#\w+" l)]
    [(keyword d) (parse-long s) color
     ({\0 :R \1 :D \2 :L \3 :U} (last color))
     (Integer/parseInt (reduce str (butlast (rest color))) 16)]))

(def data (map parse-line (read-data 2023 18)))

(defn move [[row col] [dir steps]]
  (case dir
    :U [(- row steps) col]
    :D [(+ row steps) col]
    :L [row (- col steps)]
    :R [row (+ col steps)]))

(defn vertical-parts [corners]
  (->> corners
       (partition 2 1)
       (filter (fn [[[_ b] [_ d]]] (= b d)))
       (map (fn [[[a _] [c d]]] (if (< a c) [a c d] [c a d])))
       (sort-by last)))

(defn cast-ray [lst [a b]]
  (let [mid (/ (+ a b) 2)]
    (->> (filter (fn [[a1 b1]] (< a1 mid b1)) lst)
         (map last)
         (partition 2)
         (map (fn [[c1 c2]] [a b c1 c2])))))

(defn box-area [[a b c d]] (* (inc (- b a)) (inc (- d c))))

(defn box-overlaps [boxes]
  (->> (for [[_ b1 c1 d1] boxes
             [a2 _ c2 d2] boxes
             :let [overlap-size (inc (- (min d1 d2) (max c1 c2)))]
             :when (and (= b1 a2) (pos? overlap-size))]
         overlap-size)
       (reduce +)))

(defn volume
  ([data] (volume data false))
  ([data shift?]
   (let [corners (reductions move [0 0] (if shift? (map (partial drop 3) data) data))
         verticals (vertical-parts corners)
         rows (partition 2 1 (distinct (sort (map first corners))))
         boxes (mapcat (partial cast-ray verticals) rows)]
     (reduce + (- (box-overlaps boxes)) (map box-area boxes)))))

(def part-1 (volume data))
;; => 40745

(def part-2 (volume data true))
;; => 90111113594927

;; viz

(c2d/with-canvas [c (c2d/canvas 1000 800 :low)]
  (c2d/set-background c :black)
  (c2d/translate c 150 400)
  (c2d/scale c 2)
  (doseq [[[[row1 col1] [row2 col2]] z] (map vector (partition 2 1 (reductions move [0 0] data)) data)]
    (c2d/set-color c (last z))
    (c2d/line c col1 row1 col2 row2))
  #_(c2d/save c "images/advent_of_code_2023/day18.png")
  (utils/show-image c))
