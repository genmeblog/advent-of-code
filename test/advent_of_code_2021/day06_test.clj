(ns advent-of-code-2021.day06-test
  (:require [advent-of-code-2021.day06 :as sut]
            [clojure.test :as t]))

(def data [3 4 3 1 2])

(t/deftest test-data
  (t/is (= 26 (sut/lanternfish-count data 18)))
  (t/is (= 5934 (sut/lanternfish-count data 80)))
  (t/is (= 26984457539 (sut/lanternfish-count data 256))))

(t/deftest sut
  (t/is (= 394994 sut/part-1))
  (t/is (= 1765974267455 sut/part-2)))
