(ns advent-of-code-2020.day01
  (:require [advent-of-code-2020.common :refer [read-data]]
            [fastmath.core :as m]))

(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)
(m/use-primitive-operators)

(def data (->> (read-data 1)
               (map read-string)
               (sort)))

(defmacro make-reporter
  [cnt]
  (let [ids (map #(with-meta % {:tag 'long}) (repeatedly cnt gensym))]
    `(fn [~'xs]
       (first (for [~@(interleave ids (repeat 'xs))
                    :let [s# (+ ~@ids)]
                    :when (= 2020 s#)
                    :while (= 2020 s#)]
                (* ~@ids))))))

(def process-expense-report-2 (make-reporter 2))

(def part-1 (time (process-expense-report-2 data)))

part-1
;; => 866436

(def process-expense-report-3 (make-reporter 3))

(def part-2 (time (process-expense-report-3 data)))

part-2
;; => 276650720
