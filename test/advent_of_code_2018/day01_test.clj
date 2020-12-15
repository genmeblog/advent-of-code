(ns advent-of-code-2018.day01-test
  (:require [advent-of-code-2018.day01 :as sut]
            [clojure.test :as t]))

;; sum skipped

(t/deftest repeating
  (t/is (= 2 (sut/freq-dup [1 -2 3 1])))
  (t/is (= 0 (sut/freq-dup [1 -1])))
  (t/is (= 10 (sut/freq-dup [3 3 4 -2 -4])))
  (t/is (= 5 (sut/freq-dup [-6 3 8 5 -6])))
  (t/is (= 14 (sut/freq-dup [7 7 -2 -7 -4]))))

(t/deftest sut
  (t/is (= 582 sut/part-1))
  (t/is (= 488 sut/part-2)))
