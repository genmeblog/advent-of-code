(ns advent-of-code-2015.day06
  (:require [fastmath.core :as m]            
            [common :refer [read-data]]))

(set! *unchecked-math* :warn-on-boxed)
(set! *warn-on-reflection* true)

(def buffer (long-array 1e6))

(defn toggle [^long off] (aset ^longs buffer off (m/bit-xor (aget ^longs buffer off) 1)))
(defn turn-on [^long off] (aset ^longs buffer off 1))
(defn turn-off [^long off] (aset ^longs buffer off 0))

(defn perform-operation
  [op [^long sx ^long sy ^long ex ^long ey]]
  (doseq [^long y (range sy (inc ey))
          :let [yy (* y 1000)]]
    (doseq [^long x (range sx (inc ex))]
      (op (+ x yy)))))

(defn clean [] (perform-operation turn-off [0 0 999 999]))

(defn parse-command
  [m command-string]
  (let [splitted (rest (re-find #"(\w+) (\d+),(\d+) through (\d+),(\d+)" command-string))]
    (perform-operation (m (first splitted)) (map parse-long (rest splitted)))))

(def commands (read-data 2015 6))

(defn perform
  [op1 op2 op3 commands]
  (clean)
  (run! (partial parse-command {"toggle" op1 "on" op2 "off" op3}) commands)
  (reduce + buffer))

(def part-1 (perform toggle turn-on turn-off commands))
;; => 569999

(defn toggle2 [^long off] (aset ^longs buffer off (+ 2 (aget ^longs buffer off))))
(defn turn-on2 [^long off] (aset ^longs buffer off (inc (aget ^longs buffer off))))
(defn turn-off2 [^long off] (aset ^longs buffer off (max 0 (dec (aget ^longs buffer off)))))

(def part-2 (perform toggle2 turn-on2 turn-off2 commands))
;; => 17836115
