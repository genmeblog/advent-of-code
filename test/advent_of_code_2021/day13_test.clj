(ns advent-of-code-2021.day13-test
  (:require [advent-of-code-2021.day13 :as sut]
            [clojure.test :as t]
            [common]))

(def data (sut/parser (common/str-as-blocks "6,10
0,14
9,10
0,3
10,4
4,11
6,0
6,12
4,1
0,13
10,12
3,4
3,0
8,4
1,10
2,14
8,10
9,0

fold along y=7
fold along x=5")))

(t/deftest test-data
  (t/are [f v] (= v (count (distinct (f (sut/process data)))))
    second 17
    last 16))

(t/deftest sut
  (t/is (= 647 sut/part-1)))
