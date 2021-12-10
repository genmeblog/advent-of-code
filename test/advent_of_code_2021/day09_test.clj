(ns advent-of-code-2021.day09-test
  (:require [advent-of-code-2021.day09 :as sut]
            [clojure.test :as t]))

(def data (sut/parser ["2199943210"
                     "3987894921"
                     "9856789892"
                     "8767896789"
                     "9899965678"]))

(t/deftest test-data
  (t/is (= 15 (sut/risk-level data)))
  (t/is (= 1134 (sut/biggest-3 data))))

(t/deftest sut
  (t/is (= 585 sut/part-1))
  (t/is (= 827904 sut/part-2)))
