(ns advent-of-code-2019.day01
  (:require [clojure.java.io :as io]))

(set! *unchecked-math* :warn-on-boxed)
(set! *warn-on-reflection* true)

;; required fuel formula
(defn required-fuel ^long [^long in] (- (int (/ in 3)) 2))

;; load data and calculate required fuel
(def fuel-data (map (comp required-fuel read-string)
                    (-> (io/resource "day01.txt")
                        (io/reader)
                        (line-seq))))

;; sum of required fuel
(def part-1 (reduce + fuel-data))

;; total fuel, recurence version
(defn required-fuel-total
  ^long [^long in]
  (if-not (pos? in) 0 (+ in (required-fuel-total (required-fuel in)))))

(def part-2 (reduce + (map required-fuel-total fuel-data)))
