(ns advent-of-code-2020.day18-test
  (:require [advent-of-code-2020.day18 :as sut]
            [clojure.test :as t]))

(def d1 "1 + 2 * 3 + 4 * 5 + 6")
(def d2 "1 + (2 * 3) + (4 * (5 + 6))")
(def d3 "2 * 3 + (4 * 5)")
(def d4 "5 + (8 * 3 + 9 + 3 * 4 * 3)")
(def d5 "5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))")
(def d6 "((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2")

(t/deftest sums
  (t/are [res d] (= res (-> d sut/prepare sut/->tree sut/calculate))
    71 d1 51 d2 26 d3 437 d4 12240 d5 13632 d6)
  (t/are [res d] (= res (-> d sut/prepare sut/->tree sut/fix-order sut/calculate))
    231 d1 51 d2 46 d3 1445 d4 669060 d5 23340 d6))

(t/deftest sut
  (t/is (= 800602729153 sut/part-1))
  (t/is (= 92173009047076 sut/part-2)))
