(ns advent-of-code-2021.day24-test
  (:require [advent-of-code-2021.day24 :as sut]
            [clojure.test :as t]))

(t/deftest sut
  (t/is (= 91297395919993 sut/part-1))
  (t/is (= 71131151917891 sut/part-2)))
