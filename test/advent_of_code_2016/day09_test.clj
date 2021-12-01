(ns advent-of-code-2016.day09-test
  (:require [advent-of-code-2016.day09 :as sut]
            [clojure.test :as t]))

(t/deftest part-1-test
  (t/are [s cnt] (= cnt (sut/parse-data s))
    "ADVENT" 6
    "A(1x5)BC" 7
    "(3x3)XYZ" 9
    "A(2x2)BCD(2x2)EFG" 11
    "(6x1)(1x3)A" 6
    "X(8x2)(3x3)ABCY" 18))

(t/deftest part-2-test
  (t/are [s cnt] (= cnt (sut/parse-data-all s))
    "(3x3)XYZ" 9
    "X(8x2)(3x3)ABCY" 20
    "(27x12)(20x12)(13x14)(7x10)(1x12)A" 241920
    "(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN" 445))

(t/deftest sut
  (t/is (= 74532 sut/part-1))
  (t/is (= 11558231665 sut/part-2)))
