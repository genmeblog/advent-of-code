(ns advent-of-code-2024.day03-test
  (:require [advent-of-code-2024.day03 :as sut]
            [clojure.test :as t]
            [common :refer [read-single-line]]))

(def data1 (read-single-line "examples" 2024 03))
(def data2 (read-single-line "examples" 2024 03 1))

(t/deftest examples
  (t/is (= 161 (sut/multiply-and-add (sut/find-all data1))))
  (t/is (= 48 (sut/multiply-and-add (sut/find-surrounded data2)))))

(t/deftest sut
  (t/is (= 188741603 sut/part-1))
  (t/is (= 67269798 sut/part-2)))
