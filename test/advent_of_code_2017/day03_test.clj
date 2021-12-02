(ns advent-of-code-2017.day03-test
  (:require [advent-of-code-2017.day03 :as sut]
            [clojure.test :as t]))

(t/deftest part-1-tests
  (t/is (zero? (sut/ring-dist 1)))
  (t/is (= 3 (sut/ring-dist 12)))
  (t/is (= 2 (sut/ring-dist 23)))
  (t/is (= 31 (sut/ring-dist 1024))))

(t/deftest part-2-tests
  (t/is (= 10 (sut/spiral2 5)))
  (t/is (= 25 (sut/spiral2 23)))
  (t/is (= 122 (sut/spiral2 59)))
  (t/is (= 351 (sut/spiral2 330)))
  (t/is (= 806 (sut/spiral2 747))))

(t/deftest sut
  (t/is (= 430 sut/part-1))
  (t/is (= 312453 sut/part-2)))
