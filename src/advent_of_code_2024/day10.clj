(ns advent-of-code-2024.day10
  (:require [common :refer [read-data]]
            [clojure2d.core :as c2d]
            [clojure2d.extra.utils :refer [show-image]]
            [fastmath.vector :as v]
            [clojure2d.color :as c]))

(defn parse-line [line] (mapv (comp parse-long str) line))

(def data (mapv parse-line (read-data 2024 10)))

(defn find-id [data id]
  (let [s (range (count data))]
    (for [row s col s :let [v (get-in data [row col])] :when (and v (= id v))]
      [row col])))

(defn surroundings [data v [row col]]
  (filter #(= v (get-in data %)) [[(inc row) col] [(dec row) col]
                                  [row (inc col)] [row (dec col)]]))

(defn traverse-one
  ([data method start] (traverse-one data method 0 [start]))
  ([data method curr poss]
   (if (= curr 9)
     (count poss)
     (recur data method (inc curr) (method (mapcat (partial surroundings data (inc curr)) poss))))))

(defn traverse [data method]
  (transduce (map (partial traverse-one data method)) + (find-id data 0)))

(def part-1 (traverse data distinct))
;; => 638

(def part-2 (traverse data identity))
;; => 1289


;;;

(defn build-paths
  ([data start] (build-paths data 0 [(list start)]))
  ([data curr poss]
   (if (= curr 9)
     poss
     (let [ncurr (inc curr)]
       (recur data ncurr
              (mapcat (fn [p]
                        (let [surr (surroundings data ncurr (first p))]
                          (map (partial conj p) surr))) poss))))))

(defn draw-map [data]
  (let [zeros (find-id data 0)]
    (c2d/with-canvas [c (c2d/canvas 800 800 :highest)]
      (c2d/set-background c (c/gray 20))
      (c2d/translate c 35 35)
      (c2d/set-color c (c/gray 200 200))
      (doseq [z zeros
              p (build-paths data z)
              :let [p2 (map #(v/mult % 15) p)]]
        (c2d/path-bezier c p2))
      (c2d/set-color c :green)
      (doseq [z zeros :let [[x y] (v/mult z 15)]]
        (c2d/ellipse c x y 10 10))
      (c2d/set-color c :red)
      (doseq [z (find-id data 9) :let [[x y] (v/mult z 15)]]
        (c2d/ellipse c x y 10 10))
      (c2d/save c "images/advent_of_code_2024/day10.jpg")
      (show-image c))))

(comment (draw-map data))
