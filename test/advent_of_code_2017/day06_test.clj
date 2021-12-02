(ns advent-of-code-2017.day06-test
  (:require [advent-of-code-2017.day06 :as sut]
            [clojure.test :as t]))

(def data [0 2 7 0])

(t/deftest part-1-tests
  (t/is (= 5 (first (sut/iterator data)))))

(t/deftest part-2-tests
  (t/is (= 4 (first (sut/iterator (second (sut/iterator data)))))))

(t/deftest sut
  (t/is (= 5042 sut/part-1))
  (t/is (= 1086 sut/part-2)))
