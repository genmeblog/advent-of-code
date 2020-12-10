(ns advent-of-code-2015.day06
  (:require [fastmath.core :as m]
            [clojure.string :as s]
            [clojure.java.io :as io]))

(set! *unchecked-math* :warn-on-boxed)
(set! *warn-on-reflection* true)

(def buffer (long-array 1e6))

(defn toggle [^long off] (aset ^longs buffer off (m/bit-xor (aget ^longs buffer off) 1)))
(defn turn-on [^long off] (aset ^longs buffer off 1))
(defn turn-off [^long off] (aset ^longs buffer off 0))

(defn perform-operation
  [op [sx sy] [ex ey]]
  (doseq [^long y (range sy (inc ^long ey))
          :let [yy (* y 1000)]]
    (doseq [^long x (range sx (inc ^long ex))]
      (op (+ x yy)))))

(defn clean [] (perform-operation turn-off [0 0] [999 999]))

(defn parse-numbers
  [n]
  (let [[^String x ^String y] (s/split n #",")]
    [(Long/valueOf x) (Long/valueOf y)]))

(defn parse-command
  [ft fon foff cmd]
  (let [s (s/split cmd #" ")]
    (if (= "toggle" (first s))
      (perform-operation ft
                         (parse-numbers (second s))
                         (parse-numbers (nth s 3)))
      (perform-operation (if (= "on" (second s)) fon foff)
                         (parse-numbers (nth s 2))
                         (parse-numbers (nth s 4))))))

(def commands (->> "day06.txt"
                   (io/resource)
                   (io/reader)
                   (line-seq)))

(defn perform
  [op1 op2 op3 commands]
  (clean)
  (run! (partial parse-command op1 op2 op3) commands)
  (reduce + buffer))

(def part-1 (perform toggle turn-on turn-off commands))

(defn toggle2 [^long off] (aset ^longs buffer off (+ 2 (aget ^longs buffer off))))
(defn turn-on2 [^long off] (aset ^longs buffer off (inc (aget ^longs buffer off))))
(defn turn-off2 [^long off] (aset ^longs buffer off (max 0 (dec (aget ^longs buffer off)))))

(def part-2 (perform toggle2 turn-on2 turn-off2 commands))
