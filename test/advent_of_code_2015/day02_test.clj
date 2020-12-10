(ns advent-of-code-2015.day02-test
  (:require [advent-of-code-2015.day02 :refer :all]
            [clojure.test :refer :all]))

(def feets-helper (comp calc-feets parse-line))
(def ribbon-helper (comp calc-ribbon parse-line))

(deftest feets
  (is (= 58 (feets-helper "2x3x4")))
  (is (= 43 (feets-helper "1x1x10"))))

(deftest feets
  (is (= 34 (ribbon-helper "2x3x4")))
  (is (= 14 (ribbon-helper "1x1x10"))))

(deftest results
  (is (= 1606483 part-1))
  (is (= 3842356 part-2)))
