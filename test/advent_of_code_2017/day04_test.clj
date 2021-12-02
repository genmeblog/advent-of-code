(ns advent-of-code-2017.day04-test
  (:require [advent-of-code-2017.day04 :as sut]
            [clojure.test :as t]))

(t/deftest part-1-tests
  (t/is (sut/valid? ["aa" "bb" "cc" "dd" "ee"]))
  (t/is (not (sut/valid? ["aa" "bb" "cc" "dd" "aa"])))
  (t/is (sut/valid? ["aa" "bb" "cc" "dd" "aaa"])))

(t/deftest part-2-tests
  (t/is (sut/valid2? ["abcde" "fghij"]))
  (t/is (not (sut/valid2? ["abcde" "xyz" "ecdab"])))
  (t/is (sut/valid2? ["a" "ab" "abc" "abd" "abf" "abj"]))
  (t/is (sut/valid2? ["iiii" "oiii" "ooii" "oooi" "oooo"]))
  (t/is (not (sut/valid2? ["oiii" "ioii" "iioi" "iiio"]))))

(t/deftest sut
  (t/is (= 455 sut/part-1))
  (t/is (= 186 sut/part-2)))
