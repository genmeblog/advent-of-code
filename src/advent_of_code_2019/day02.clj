(ns advent-of-code-2019.day02
  (:require [clojure.java.io :as io]
            [clojure.string :as s]))

(set! *unchecked-math* :warn-on-boxed)
(set! *warn-on-reflection* true)

;; load data
(def program (mapv read-string
                   (-> (io/resource "day02.txt")
                       (slurp)
                       (s/split #","))))

;; program creator with initial data
(defn make-program
  [noun verb]
  (-> program
      (assoc 1 noun)
      (assoc 2 verb)))

;; execute opcode
(defn process
  [f program ^long iter]
  (assoc program (program (+ iter 3))
         (f (program (program (inc iter)))
            (program (program (+ iter 2))))))

(defn executor
  ([program] (executor program 0))
  ([program ^long iter]
   (condp = (program iter)
     1 (recur (process + program iter) (+ iter 4))
     2 (recur (process * program iter) (+ iter 4))
     99 program)))

(def executor-0 (comp first executor make-program))

(def part-1 (executor-0 12 2))

(set! *unchecked-math* true)

;; OBSERVED
;;
;; difference between consecutive nouns for given verb (any) is constant
;; verb is added to the difference
(def difference
  (let [r-verb (int (rand 100))]
    (->> (range 100)
         (map #(executor-0 % r-verb))
         (partition 2 1)
         (map (partial apply -))
         (distinct)
         (first)
         (-))))
;; => 460800

(def offset (executor-0 0 0))
;; => 797908

(def target 19690720)
(def target- (- target offset))

(def noun (quot target- difference))
(def verb (mod target- difference))

(def part-2 (+ verb (* 100 noun)))
