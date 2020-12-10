(ns advent-of-code-2019.day02-test
  (:require [advent-of-code-2019.day02 :refer :all]
            [clojure.test :refer :all]))

(deftest executor-test
  (is (= [3500 9 10 70 2 3 11 0 99 30 40 50] (executor [1 9 10 3 2 3 11 0 99 30 40 50])))
  (is (= [2 0 0 0 99] (executor [1 0 0 0 99])))
  (is (= [2 3 0 6 99] (executor [2 3 0 3 99])))
  (is (= [2 4 4 5 99 9801] (executor [2 4 4 5 99 0])))
  (is (= [30 1 1 4 2 5 6 0 99] (executor [1 1 1 4 99 5 6 0 99]))))

(deftest analysis-part
  (is (= 460800 difference))
  (is (every? true? (for [a (range 100)
                          b (range 100)]
                      (let [c (int (rand 100))]
                        (= (- a b)
                           (- (executor-0 c a) (executor-0 c b)))))))
  (is (= 797908 (executor-0 0 0)))
  (is (= noun 41))
  (is (= verb 12))
  (is (= target (executor-0 noun verb))))

(deftest brute-force
  (is (= [41 12] (first (for [a (range 100)
                              b (range 100)
                              :let [res (executor-0 a b)]
                              :when (= res target)]
                          [a b])))))

(deftest results
  (is (= 6327510 part-1))
  (is (= 4112 part-2)))
