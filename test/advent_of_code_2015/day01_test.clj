(ns advent-of-code-2015.day01-test
  (:require [advent-of-code-2015.day01 :refer :all]
            [clojure.test :refer :all]))

(defn process-data [d] (reduce + (map convert d)))

(deftest part-1-tests
  (is (zero? (process-data "(())")))
  (is (zero? (process-data "))((")))
  (is (= 3 (process-data "(((")))
  (is (= 3 (process-data "(()(()(")))
  (is (= 3 (process-data "))(((((")))
  (is (= -1 (process-data "())")))
  (is (= -1 (process-data "))(")))
  (is (= -3 (process-data ")))")))
  (is (= -3 (process-data ")())())"))))

(deftest part-2-tests
  (is (= 1 (find-step (map convert ")"))))
  (is (= 5 (find-step (map convert "()())")))))

(deftest results
  (is (= 138 part-1))
  (is (= 1771 part-2)))
