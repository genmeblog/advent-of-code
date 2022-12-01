(ns advent-of-code-2019.day01-test
  (:require [advent-of-code-2019.day01 :as sut]
            [clojure.test :as t]))

(t/deftest required-fuel-test
  (t/is (= 2 (sut/required-fuel 12)))
  (t/is (= 2 (sut/required-fuel 14)))
  (t/is (= 654 (sut/required-fuel 1969)))
  (t/is (= 33583 (sut/required-fuel 100756))))

(def required-total (comp sut/required-fuel-total sut/required-fuel))

(t/deftest total-fuel-test
  (t/is (= 2 (required-total 14)))
  (t/is (= 966 (required-total 1969)))
  (t/is (= 50346 (required-total 100756))))

(t/deftest results
  (t/is (= 3497998 sut/part-1))
  (t/is (= 5244112 sut/part-2)))
