(ns advent-of-code-2019.day02-test
  (:require [advent-of-code-2019.day02 :as sut]
            [clojure.test :as t]))

(t/deftest executor-test
  (t/is (= [3500 9 10 70 2 3 11 0 99 30 40 50] (sut/executor [1 9 10 3 2 3 11 0 99 30 40 50])))
  (t/is (= [2 0 0 0 99] (sut/executor [1 0 0 0 99])))
  (t/is (= [2 3 0 6 99] (sut/executor [2 3 0 3 99])))
  (t/is (= [2 4 4 5 99 9801] (sut/executor [2 4 4 5 99 0])))
  (t/is (= [30 1 1 4 2 5 6 0 99] (sut/executor [1 1 1 4 99 5 6 0 99]))))

(t/deftest analysis-part
  (t/is (= 460800 sut/difference))
  (t/is (every? true? (for [a (range 100)
                            b (range 100)]
                        (let [c (int (rand 100))]
                          (= (- a b)
                             (- (sut/executor-0 c a) (sut/executor-0 c b)))))))
  (t/is (= 797908 (sut/executor-0 0 0)))
  (t/is (= sut/noun 41))
  (t/is (= sut/verb 12))
  (t/is (= sut/target (sut/executor-0 sut/noun sut/verb))))

(t/deftest brute-force
  (t/is (= [41 12] (first (for [a (range 100)
                                b (range 100)
                                :let [res (sut/executor-0 a b)]
                                :when (= res sut/target)]
                            [a b])))))

(t/deftest results
  (t/is (= 6327510 sut/part-1))
  (t/is (= 4112 sut/part-2)))
