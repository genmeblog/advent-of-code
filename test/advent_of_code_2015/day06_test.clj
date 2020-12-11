(ns advent-of-code-2015.day06-test
  (:require [advent-of-code-2015.day06 :as sut]
            [clojure.test :as t]))

(def p1 (partial sut/perform sut/toggle sut/turn-on sut/turn-off))
(def p2 (partial sut/perform sut/toggle2 sut/turn-on2 sut/turn-off2))

(t/deftest calculations
  (t/is (= 9 (p1 ["turn on 0,0 through 2,2"])))
  (t/is (= 1000000 (p1 ["turn on 0,0 through 999,999"])))
  (t/is (= 1000 (p1 ["toggle 0,0 through 999,0"])))
  (t/is (= 0 (p1 ["turn off 499,499 through 500,500"])))
  (t/is (= 1 (p2 ["turn on 0,0 through 0,0"])))
  (t/is (= 2000000 (p2 ["toggle 0,0 through 999,999"]))))

(t/deftest sut
  (t/is (= 569999 sut/part-1))
  (t/is (= 17836115 sut/part-2)))
