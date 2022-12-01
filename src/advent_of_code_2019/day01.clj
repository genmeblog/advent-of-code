(ns advent-of-code-2019.day01
  (:require [common :refer [read-data]]))

;; required fuel formula
(defn required-fuel ^long [^long in] (- (int (/ in 3)) 2))

;; load data and calculate required fuel
(def fuel-data (map (comp required-fuel read-string) (read-data 2019 1)))

;; sum of required fuel
(def part-1 (reduce + fuel-data))

;; total fuel, recurence version
(defn required-fuel-total
  ^long [^long in]
  (if-not (pos? in) 0 (+ in (required-fuel-total (required-fuel in)))))

(def part-2 (reduce + (map required-fuel-total fuel-data)))
