(ns advent-of-code-2020.day06-test
  (:require [advent-of-code-2020.day06 :as sut]
            [advent-of-code-2020.common :refer [str-as-blocks]]
            [clojure.test :as t]))

(def data (str-as-blocks "abc

a
b
c

ab
ac

a
a
a
a

b"))

(t/deftest total-yes-answers
  (t/is (= 11 (sut/total-yes-answers data))))

(t/deftest total-common-yes-answers
  (t/is (= 6 (sut/total-common-yes-answers data))))
