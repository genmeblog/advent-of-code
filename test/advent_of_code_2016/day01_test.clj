(ns advent-of-code-2016.day01-test
  (:require [advent-of-code-2016.day01 :as sut]
            [clojure.test :as t]))

(t/deftest part-1-tests
  (t/is (= 5 (sut/process-part-1 ["R2" "L3"])))
  (t/is (= 2 (sut/process-part-1 ["R2" "R2" "R2"])))
  (t/is (= 12 (sut/process-part-1 ["R5" "L5" "R5" "R3"]))))

(t/deftest part-2-tests
  (t/is (= 4 (sut/process-part-2 ["R8" "R4" "R4" "R8"]))))

(t/deftest sut
  (t/is (= 242 sut/part-1))
  (t/is (= 150 sut/part-2)))
