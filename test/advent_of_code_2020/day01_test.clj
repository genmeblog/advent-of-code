(ns advent-of-code-2020.day01-test
  (:require [advent-of-code-2020.day01 :as sut]
            [clojure.test :as t]))

(t/deftest part-1
  (t/is (= 514579 (sut/process-expense-report-2 [1721 979 366 299 675 1456]))))

(t/deftest part-2
  (t/is (= 241861950 (sut/process-expense-report-3 [1721 979 366 299 675 1456]))))

(t/deftest sut
  (t/is (= 866436 sut/part-1))
  (t/is (= 276650720 sut/part-2)))
