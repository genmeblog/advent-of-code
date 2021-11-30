(ns advent-of-code-2016.day02-test
  (:require [advent-of-code-2016.day02 :as sut]
            [clojure.test :as t]))

(def data ["ULL"
           "RRDDD"
           "LURDL"
           "UUUUD"])

(t/deftest part-1-tests
  (t/is (= "1985" (sut/instructions-1 data))))

(t/deftest part-2-tests
  (t/is (= "5DB3" (sut/instructions-2 data))))

(t/deftest sut
  (t/is (= "36629" sut/part-1))
  (t/is (= "99C3D" sut/part-2)))
