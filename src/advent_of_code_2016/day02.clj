(ns advent-of-code-2016.day02
  (:require [common :refer [read-data]]))

(def data (read-data 2016 2))

(def cdec (comp (partial max 0) dec))
(def cinc (comp (partial min 2) inc))

(defn move-pad-1
  [pos direction]
  (case direction
    \L (update pos 0 cdec)
    \R (update pos 0 cinc)
    \U (update pos 1 cdec)
    \D (update pos 1 cinc)))

(defn pos->number [[x y]] (inc (+ x (* y 3))))

(defn instructions-1
  [data]
  (->> data
       (reductions #(reduce move-pad-1 %1 %2) [1 1])
       (rest)
       (map pos->number)
       (apply str)))

(def part-1 (instructions-1 data))
;; => "36629"

(defn get-value
  [in v x y]
  (let [n (get-in in [y x])]
    (if (= n \0) v n)))

(defn process-keypad-2
  [in]
  (for [x (range 7)
        y (range 7)
        :let [v (get-in in [y x])]
        :when (not= v \0)
        :let [f (partial get-value in v)]]
    [v (zipmap "LRUD" [(f (dec x) y)
                       (f (inc x) y)
                       (f x (dec y))
                       (f x (inc y))])]))

(def keypad-2 (->> ["0000000"
                    "0001000"
                    "0023400"
                    "0567890"
                    "00ABC00"
                    "000D000"
                    "0000000"]
                   (mapv vec)
                   (process-keypad-2)
                   (into {})))

(defn move-pad-2
  [pos direction]
  (get-in keypad-2 [pos direction]))

(defn instructions-2
  [data]
  (->> data
       (reductions #(reduce move-pad-2 %1 %2) \5)
       (rest)
       (apply str)))

(def part-2 (instructions-2 data))
;; => "99C3D"
