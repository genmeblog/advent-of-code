(ns advent-of-code-2017.day02-test
  (:require [advent-of-code-2017.day02 :as sut]
            [clojure.test :as t]))

(def data1 (map sort [[5 1 9 5]
                      [7 5 3]
                      [2 4 6 8]]))

(t/deftest part-1-tests
  (t/is (= 18 (sut/checksum data1))))

(def data2 [[5 9 2 8]
            [9 4 7 3]
            [3 8 6 5]])

(t/deftest part-2-tests
  (t/is (= 9 (sut/divisibility data2))))

(t/deftest sut
  (t/is (= 32020 sut/part-1))
  (t/is (= 236 sut/part-2)))
