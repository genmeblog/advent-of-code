(ns advent-of-code-2021.day17-test
  (:require [advent-of-code-2021.day17 :as sut]
            [clojure.test :as t]))

(def data (sut/scan {:x [20 30] :y [-10 -5]}))

(t/deftest test-data
  (t/is (= 45 (reduce max data)))
  (t/is (= 112 (count data))))

(t/deftest sut
  (t/is (= 4278 sut/part-1))
  (t/is (= 1994 sut/part-2)))
