(ns advent-of-code-2020.day11-test
  (:require [advent-of-code-2020.day11 :as sut]
            [clojure.test :as t]
            [clojure.string :as str]))

(def data (sut/->seats (str/split-lines "L.LL.LL.LL
LLLLLLL.LL
L.L.L..L..
LLLL.LL.LL
L.LL.LL.LL
L.LLLLL.LL
..L.L.....
LLLLLLLLLL
L.LLLLLL.L
L.LLLLL.LL")))

(t/deftest part-1
  (t/is (= 37 (sut/occupation-state data sut/step-near)))
  (t/is (= 26 (sut/occupation-state (sut/with-index data) sut/step-far))))

(t/deftest sut
  (t/is (= 2359 sut/part-1))
  (t/is (= 2131 sut/part-2)))
