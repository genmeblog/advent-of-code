(ns advent-of-code-2015.day04-test
  (:require [advent-of-code-2015.day04 :as sut]
            [clojure.test :as t]))

(t/deftest part-1-test
  (t/is (= 609043 (sut/find-md5 sut/five0? "abcdef")))
  (t/is (= 1048970 (sut/find-md5 sut/five0? "pqrstuv"))))

(t/deftest sut
  (t/is (= 346386 sut/part-1))
  (t/is (= 9958218 sut/part-2)))

