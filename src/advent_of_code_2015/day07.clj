(ns advent-of-code-2015.day07
  (:require [common :refer [read-data]]
            [clojure.string :as str]))

(def ops {"AND" bit-and
          "OR" bit-or
          "LSHIFT" bit-shift-left
          "RSHIFT" unsigned-bit-shift-right})

(defn calc-wire-value [m wire] (let [wire-val (m wire)]
                                 (cond
                                   (fn? wire-val) (wire-val m)
                                   (symbol? wire-val) (let [m2 (calc-wire-value m wire-val)]
                                                        (assoc m2 wire (m2 wire-val)))
                                   (number? wire) (assoc m wire wire)
                                   :else m)))
(defn binary-op
  [op wire left right]
  (fn [m]
    (let [newm (-> m
                   (calc-wire-value left)
                   (calc-wire-value right))]                             
      (assoc newm wire (bit-and 0xffff ^long (op (newm left) (newm right)))))))

(defn not-op
  [wire arg]
  (fn [m]
    (let [newm (calc-wire-value m arg)]
      (assoc newm wire (bit-and 0xffff ^long (bit-not (newm arg)))))))

(defn parse
  [s]
  (let [[_ op-call wire] (re-find #"(.+) -> (.+)" s)
        wire (read-string wire)]
    [wire (if-let [[_ left op right] (re-find #"(.+)\s([A-Z]+)\s(.+)" op-call)]
            (binary-op (ops op) wire (read-string left) (read-string right))
            (if (str/starts-with? op-call "NOT")
              (not-op wire (read-string (subs op-call 4)))
              (read-string op-call)))]))

(defn wire-value
  [m wire]
  ((calc-wire-value m wire) wire))

(def data (into {} (map parse (read-data 2015 7))))

(def part-1 (wire-value data 'a))
;; => 16076

;;

(def part-2 (-> data
                (assoc 'b (wire-value data 'a))
                (wire-value 'a)))
;; => 2797
