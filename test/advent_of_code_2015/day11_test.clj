(ns advent-of-code-2015.day11-test
  (:require [advent-of-code-2015.day11 :as sut]
            [clojure.test :as t]))

(t/deftest examples
  (t/is (sut/invalid-password? "hijklmmn"))
  (t/is (sut/invalid-password? "abbceffg"))
  (t/is (sut/invalid-password? "abbcegjk"))
  (t/is (= "abcdffaa" (sut/next-password "abcdefgh"))))

(t/deftest sut
  (t/is (= "hxbxxyzz" sut/part-1))
  (t/is (= "hxcaabcc" sut/part-2)))
