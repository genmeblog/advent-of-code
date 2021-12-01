(ns advent-of-code-2016.day05-test
  (:require [advent-of-code-2016.day05 :as sut]
            [clojure.test :as t]))

(t/deftest part-1-tests
  (t/is (= "18f47a30" (sut/password "abc"))))

(t/deftest part-2-tests
  (t/is (= "05ace8e3" (sut/position-password "abc"))))

(t/deftest sut
  (t/is (= "d4cd2ee1" sut/part-1))
  (t/is (= "f2c730e5" sut/part-2)))
