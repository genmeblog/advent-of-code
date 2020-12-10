(ns advent-of-code-2015.day04-test
  (:require [advent-of-code-2015.day04 :refer :all]
            [clojure.test :refer :all]))

(deftest part-1-test
  (is (= 609043 (find-md5 five0? "abcdef")))
  (is (= 1048970 (find-md5 five0? "pqrstuv"))))

(deftest results
  (is (= 346386 part-1))
  (is (= 9958218 part-2)))

