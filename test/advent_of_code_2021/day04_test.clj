(ns advent-of-code-2021.day04-test
  (:require [advent-of-code-2021.day04 :as sut]
            [clojure.test :as t]))

(def data (-> [["7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1"]

               ["22 13 17 11  0"
                "8  2 23  4 24"
                "21  9 14 16  7"
                "6 10  3 18  5"
                "1 12 20 15 19"]

               ["3 15  0  2 22"
                "9 18 13 17  5"
                "19  8  7 25 23"
                "20 11 10 24  4"
                "14 21 16 12  6"]

               ["14 21 17 24  4"
                "10 16 15  9 19"
                "18  8 23 26 20"
                "22 11 13  6  5"
                "2  0 12  3  7"]]
              (sut/process-data)))

(t/deftest test-data
  (t/are [f s] (= s (sut/score (f (sut/find-winning-boards data))))
    first 4512
    last 1924))

(t/deftest sut
  (t/is (= 58838 sut/part-1))
  (t/is (= 6256 sut/part-2)))
