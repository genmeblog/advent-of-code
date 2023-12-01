(ns advent-of-code-2023.day01-test
  (:require [advent-of-code-2023.day01 :as sut]
            [clojure.test :as t]))

(def data-1 ["1abc2"
           "pqr3stu8vwx"
           "a1b2c3d4e5f"
           "treb7uchet"])

(def data-2 ["two1nine"
           "eightwothree"
           "abcone2threexyz"
           "xtwone3four"
           "4nineeightseven2"
           "zoneight234"
           "7pqrstsixteen"])

(t/deftest examples
  (t/is (= 142 (sut/part-1-solution data-1)))
  (t/is (= 281 (sut/part-2-solution data-2))))

(t/deftest sut
  (t/is (= 55029 sut/part-1))
  (t/is (= 55686 sut/part-2)))
