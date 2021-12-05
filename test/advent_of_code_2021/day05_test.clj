(ns advent-of-code-2021.day05-test
  (:require [advent-of-code-2021.day05 :as sut]
            [common :refer [parse]]
            [clojure.test :as t]))

(def data (->> ["0,9 -> 5,9"
                "8,0 -> 0,8"
                "9,4 -> 3,4"
                "2,2 -> 2,1"
                "7,0 -> 7,4"
                "6,4 -> 2,0"
                "0,9 -> 2,9"
                "3,4 -> 1,4"
                "0,0 -> 8,8"
                "5,5 -> 8,2"]
               (map (partial parse #"(\d+),(\d+) -> (\d+),(\d+)"))))

(t/deftest test-data
  (t/is (= 5 (sut/crosses data sut/horv)))
  (t/is (= 12 (sut/crosses data))))

(t/deftest sut
  (t/is (= 6005 sut/part-1))
  (t/is (= 23864 sut/part-2)))
