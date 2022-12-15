(ns advent-of-code-2022.day14
  (:require [common :refer [read-data split-line]]
            [clojure2d.core :as c2d]
            [clojure2d.color :as c])
  (:import [java.util TreeSet]))

(defn parse
  [line]
  (->> #"\s\-\>\s|,"
       (split-line line)
       (map parse-long)
       (partition 2)
       (partition 2 1)))

(defn range+ [a b] (if (> a b) (range b (inc a)) (range a (inc b))))

(defn add-horizontal
  [buff row span]
  (reduce (fn [buff col] (update buff col conj row)) buff span))

(defn add-line
  [buff line]
  (reduce (fn [buff [[sx sy] [ex ey]]]
            (-> (if (= sx ex)
                  (update buff sx concat (range+ sy ey))
                  (add-horizontal buff sy (range+ sx ex))))) buff line))

(defn parse-lines
  [data]
  {:input (->> (map parse data)
               (reduce add-line {}))})

(def data (parse-lines (read-data 2022 14)))

(defn nearest-rock [data col row] (when-let [rocks (data col)] (first (.tailSet ^TreeSet rocks row))))
(defn ts-add! [ts v] (let [^TreeSet nv (or ts (TreeSet.))] (.add nv v) nv))

(defn drop-point
  ([data] (drop-point data [500 0]))
  ([data [col row]]
   (if-not (:end data)
     (let [bottom (nearest-rock data col row)
           left (nearest-rock data (dec col) bottom)
           right (nearest-rock data (inc col) bottom)]
       (cond
         (or (not (and bottom left right))
             (contains? (data 500) 0)) (assoc data :end true)
         (< bottom left) (recur data [(dec col) bottom])
         (< bottom right) (recur data [(inc col) bottom])
         :else (-> data
                   (update col ts-add! (dec bottom))
                   (update :sands conj [col (dec bottom)])
                   (update :counts (fnil inc 0)))))
     data)))

(defn ->treesets [m] (assoc m :treesets (update-vals (:input m) (fn [xs] (TreeSet. xs)))))

(defn till-falling
  [data]
  (->> (->treesets data)
       (:treesets)
       (iterate drop-point)
       (drop-while (complement #(:end %)))
       (first)))

(def part-1 (:counts (till-falling data)))
;; => 592

(defn to-the-top
  [data]
  (let [mx (+ (reduce max (mapcat second (:input data))) 2)]
    (-> (update data :input add-horizontal mx (range (- 500 mx 2) (+ 500 mx 3)))
        (till-falling))))

(defonce part-2 (:counts (to-the-top data)))
;; => 30367

;; vis

(defn draw-map
  [data suffix]
  (let [size 3 size- (dec size)]
    (c2d/with-canvas [c (c2d/canvas 1200 600)]
      (c2d/set-background c 0)
      (c2d/translate c -900 50)
      (c2d/set-color c :red)
      (c2d/ellipse c 1500 0 10 10)
      (c2d/set-color c (c/gray 200))
      (doseq [[col rows] (dissoc data :sands :counts :end)
              row rows]
        (c2d/crect c (* size col) (* size row) size- size-))
      (c2d/set-color c :yellow)
      (doseq [[col row] (:sands data)]
        (c2d/crect c (* size col) (* size row) size- size-))
      (c2d/save c (str "images/advent_of_code_2022/day14" suffix ".jpg"))
      (c2d/show-window {:canvas c}))))

(comment
  (draw-map (till-falling data) "a")
  (draw-map (to-the-top data) "b"))
