(ns advent-of-code-2021.day03-test
  (:require [advent-of-code-2021.day03 :as sut]
            [clojure.test :as t]))

(def data ["00100"
           "11110"
           "10110"
           "10111"
           "10101"
           "01111"
           "00111"
           "11100"
           "10000"
           "11001"
           "00010"
           "01010"])

(t/deftest test-data
  (t/is (= 198 (sut/power-consumption data 31)))
  (t/is (= 230 (sut/life-support-rating data))))

(t/deftest sut
  (t/is (= 2967914 sut/part-1))
  (t/is (= 7041258 sut/part-2)))
