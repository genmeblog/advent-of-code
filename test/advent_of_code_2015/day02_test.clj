(ns advent-of-code-2015.day02-test
  (:require [advent-of-code-2015.day02 :as sut]
            [clojure.test :as t]))

(def feets-helper (comp sut/calc-feets sut/parse-line))
(def ribbon-helper (comp sut/calc-ribbon sut/parse-line))

(t/deftest feets-of-paper
  (t/is (= 58 (feets-helper "2x3x4")))
  (t/is (= 43 (feets-helper "1x1x10"))))

(t/deftest length-of-ribbon
  (t/is (= 34 (ribbon-helper "2x3x4")))
  (t/is (= 14 (ribbon-helper "1x1x10"))))

(t/deftest sut
  (t/is (= 1606483 sut/part-1))
  (t/is (= 3842356 sut/part-2)))
