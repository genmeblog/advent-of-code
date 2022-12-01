(ns advent-of-code-2019.day04-test
  (:require [advent-of-code-2019.day04 :as sut]
            [clojure.test :as t]))

(t/deftest criteria
  (t/is (sut/meets-criteria? "111111"))
  (t/is (not (sut/meets-criteria? "223450")))
  (t/is (not (sut/meets-criteria? "123789"))))

(t/deftest criteria-no-blocks
  (t/is (sut/meets-criteria-no-blocks? "112233"))
  (t/is (not (sut/meets-criteria-no-blocks? "123444")))
  (t/is (sut/meets-criteria-no-blocks? "111122")))

(t/deftest results
  (t/is (= 1099 sut/part-1))
  (t/is (= 710  sut/part-2)))
