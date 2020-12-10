(ns advent-of-code-2019.day04-test
  (:require [advent-of-code-2019.day04 :refer :all]
            [clojure.test :refer :all]))

(deftest criteria
  (is (meets-criteria? "111111"))
  (is (not (meets-criteria? "223450")))
  (is (not (meets-criteria? "123789"))))

(deftest criteria-no-blocks
  (is (meets-criteria-no-blocks? "112233"))
  (is (not (meets-criteria-no-blocks? "123444")))
  (is (meets-criteria-no-blocks? "111122")))

(deftest results
  (is (= 1099 part-1))
  (is (= 710 part-2)))
