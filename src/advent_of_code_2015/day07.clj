(ns advent-of-code-2015.day07
  (:require [common :refer [read-data]]
            [clojure.string :as str]))

(def two-regex )

(def ops {"AND" bit-and
          "OR" bit-or
          "LSHIFT" bit-shift-left
          "RSHIFT" unsigned-bit-shift-right
          "NOT" bit-not})

(defn call [m wire] (let [v (read-string wire)]
                      (if (number? v) v ((m wire) m))))
(defn make-operation
  [op & r] (let [op-fn (ops op)]
             (fn [m]
               (bit-and 0xffff ^long (apply op-fn (map (partial call m) r))))))

(defn parse
  [s]
  (let [[_ op-call wire] (re-find #"(.+) -> (.+)" s)]
    [wire (if-let [[_ left op right] (re-find #"(.+)\s([A-Z]+)\s(.+)" op-call)]
            (make-operation op left right)
            (if (str/starts-with? op-call "NOT")
              (make-operation "NOT" (subs op-call 4))
              #(call % op-call)))]))

(def data (into {} (map parse (read-data 2015 7))))


(call data "x")

(parse (first data))

(re-find two-regex (second (re-find wire-regex "NOT kt -> ss")))



(def data (str/split-lines "123 -> x
456 -> y
x AND y -> d
x OR y -> e
x LSHIFT 2 -> f
y RSHIFT 2 -> g
NOT x -> h
NOT y -> i"))

(let [m (into {} (map parse data))]
  (call m "i"))
