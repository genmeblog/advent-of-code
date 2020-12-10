(ns advent-of-code-2015.day06-test
  (:require [advent-of-code-2015.day06 :refer :all]
            [clojure.test :refer :all]))

(def p1 (partial perform toggle turn-on turn-off))
(def p2 (partial perform toggle2 turn-on2 turn-off2))

(deftest calculations
  (is (= 9 (p1 ["turn on 0,0 through 2,2"])))
  (is (= 1000000 (p1 ["turn on 0,0 through 999,999"])))
  (is (= 1000 (p1 ["toggle 0,0 through 999,0"])))
  (is (= 0 (p1 ["turn off 499,499 through 500,500"])))
  (is (= 1 (p2 ["turn on 0,0 through 0,0"])))
  (is (= 2000000 (p2 ["toggle 0,0 through 999,999"]))))

(deftest results
  (is (= 569999 part-1))
  (is (= 17836115 part-2)))
