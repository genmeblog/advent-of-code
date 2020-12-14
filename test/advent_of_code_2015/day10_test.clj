(ns advent-of-code-2015.day10-test
  (:require [advent-of-code-2015.day10 :as sut]
            [clojure.test :as t]))

(t/deftest game
  (t/is (= 1 (sut/look-and-say "1" 0)))
  (t/is (= 2 (sut/look-and-say "1" 1)))
  (t/is (= 2 (sut/look-and-say "1" 2)))
  (t/is (= 4 (sut/look-and-say "1" 3)))
  (t/is (= 6 (sut/look-and-say "1" 4))))

(t/deftest sut
  (t/is (= 252594 sut/part-1))
  (t/is (= 3579328 sut/part-2)))
