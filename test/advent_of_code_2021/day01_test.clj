(ns advent-of-code-2021.day01-test
  (:require [advent-of-code-2021.day01 :as sut]
            [clojure.test :as t]))

(def data [199 200 208 210 200 207 240 269 260 263])

(t/deftest part-1
  (t/is (= 7 (sut/count-incresing data))))

(t/deftest part-2
  (t/is (= 5 (sut/count-incresing-3 data))))

(t/deftest sut
  (t/is (= 1167 sut/part-1))
  (t/is (= 1130 sut/part-2)))
