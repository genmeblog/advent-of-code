(ns advent-of-code-2021.day11-test
  (:require [advent-of-code-2021.day11 :as sut]
            [clojure.test :as t]))


(def data1 (sut/parse ["11111"
                     "19991"
                     "19191"
                     "19991"
                     "11111"]))

(def data2 (sut/parse ["5483143223"
                     "2745854711"
                     "5264556173"
                     "6141336146"
                     "6357385478"
                     "4167524645"
                     "2176841721"
                     "6882881134"
                     "4846848554"
                     "5283751526"]))

(t/deftest toy-dataset
  (t/are [p r] (= r (:grid (second (nth data1 p))))
    1 [[3 4 5 4 3] [4 0 0 0 4] [5 0 0 0 5] [4 0 0 0 4] [3 4 5 4 3]]
    2 [[4 5 6 5 4] [5 1 1 1 5] [6 1 1 1 6] [5 1 1 1 5] [4 5 6 5 4]]))

(t/deftest test-dataset
  (t/is (= 204 (sut/total-flashes data2 10)))
  (t/is (= 1656 (sut/total-flashes data2)))
  (t/is (= 195 (sut/full-flash data2))))

(t/deftest sut
  (t/is (= 1585 sut/part-1))
  (t/is (= 382 sut/part-2)))
