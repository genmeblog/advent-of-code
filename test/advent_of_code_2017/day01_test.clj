(ns advent-of-code-2017.day01-test
  (:require [advent-of-code-2017.day01 :as sut]
            [clojure.test :as t]))

(t/deftest part1-tests
  (t/are [res in] (= res (sut/process-part-1 in))
    3 "1122"
    4 "1111"
    0 "1234"
    9 "91212129"))

(t/deftest part2-tests
  (t/are [res in] (= res (sut/process-part-2 in))
    6 "1212"
    0 "1221"
    4 "123425"
    12 "123123"
    4 "12131415"))

(t/deftest sut
  (t/is (= 1031 sut/part-1))
  (t/is (= 1080 sut/part-2)))

