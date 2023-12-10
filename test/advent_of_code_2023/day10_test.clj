(ns advent-of-code-2023.day10-test
  (:require [advent-of-code-2023.day10 :as sut]
            [clojure.test :as t]
            [common :refer [read-data]]))

(t/deftest examples
  (t/are [r ex] (= r (sut/farthest-point (vec (read-data "examples" 2023 10 ex))))
    8 2
    4 1)
  (t/are [r ex] (= r (sut/find-interior (vec (read-data "examples" 2023 10 ex))))
    4 3
    4 4
    8 5
    10 6))

(t/deftest sut
  (t/is (= 6682 sut/part-1))
  (t/is (= 353 sut/part-2)))
