(ns advent-of-code-2017.day05-test
  (:require [advent-of-code-2017.day05 :as sut]
            [clojure.test :as t]))

(def data [0 3 0 1 -3])

(t/deftest part-1-tests
  (t/is (= 5 (sut/iterator sut/update-1 data))))

(t/deftest part-2-tests
  (t/is (= 10 (sut/iterator sut/update-2 data))))

(t/deftest sut
  (t/is (= 315613 sut/part-1))
  (t/is (= 22570529 sut/part-2)))
