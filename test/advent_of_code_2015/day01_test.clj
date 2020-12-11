(ns advent-of-code-2015.day01-test
  (:require [advent-of-code-2015.day01 :as sut]
            [clojure.test :as t]))

(defn process-data [d] (reduce + (map sut/convert d)))

(t/deftest part-1-tests
  (t/is (zero? (process-data "(())")))
  (t/is (zero? (process-data "))((")))
  (t/is (= 3 (process-data "(((")))
  (t/is (= 3 (process-data "(()(()(")))
  (t/is (= 3 (process-data "))(((((")))
  (t/is (= -1 (process-data "())")))
  (t/is (= -1 (process-data "))(")))
  (t/is (= -3 (process-data ")))")))
  (t/is (= -3 (process-data ")())())"))))

(t/deftest part-2-tests
  (t/is (= 1 (sut/find-step (map sut/convert ")"))))
  (t/is (= 5 (sut/find-step (map sut/convert "()())")))))

(t/deftest sut
  (t/is (= 138 sut/part-1))
  (t/is (= 1771 sut/part-2)))
