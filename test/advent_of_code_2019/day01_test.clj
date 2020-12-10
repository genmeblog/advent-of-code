(ns advent-of-code-2019.day01-test
  (:require [advent-of-code-2019.day01 :refer :all]
            [clojure.test :refer :all]))

(deftest required-fuel-test
  (is (= 2 (required-fuel 12)))
  (is (= 2 (required-fuel 14)))
  (is (= 654 (required-fuel 1969)))
  (is (= 33583 (required-fuel 100756))))

(def required-total (comp required-fuel-total required-fuel))

(deftest total-fuel-test
  (is (= 2 (required-total 14)))
  (is (= 966 (required-total 1969)))
  (is (= 50346 (required-total 100756))))

(deftest results
  (is (= 3497998 part-1))
  (is (= 5244112 part-2)))
