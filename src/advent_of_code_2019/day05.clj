(ns advent-of-code-2019.day05
  (:require [clojure.java.io :as io]
            [clojure.string :as s]))

(set! *unchecked-math* true)
(set! *warn-on-reflection* true)

;; load data
(def machine {:input [1]
              :output []
              :pc 0
              :memory (mapv read-string
                            (-> (io/resource "day05.txt")
                                (slurp)
                                (s/split #",")))})

(defn step [m c] (update m :pc + c))
(defmacro R [memory a mode] `(if ~mode (~memory ~a) ~a))

(defn in
  [m _ d]
  (let [v (first (:input m))]
    (-> (update m :input rest)
        (update :memory assoc d v)
        (step 2))))

(defn out
  [{:keys [memory] :as all} [mode1 _] [d]]
  (-> (update all :output conj (R memory d mode1))
      (step 2)))

(defn binary
  [{:keys [memory] :as all} f [mode1 mode2] [d1 d2 a]]
  (-> (update all :memory assoc a (f (R memory d1 mode1)
                                     (R memory d2 mode2)))
      (step 4)))

(defn parse-opcode
  [^long in]
  (let [opcode (mod in 100)
        m1 (mod (quot in 100) 10)
        m2 (mod (quot in 1000) 10)]
    [opcode (zero? m1) (zero? m2)]))

(defn executor
  [{:keys [^long pc input output program]}]
  (let [[opcode mode1 mode2] (parse-opcode (program pc))]))

(def executor-0 (comp first executor make-program))

(def part-1 (executor-0 12 2))
