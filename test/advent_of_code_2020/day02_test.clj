(ns advent-of-code-2020.day02-test
  (:require [advent-of-code-2020.day02 :as sut]
            [clojure.test :as t]))

(def data (mapv sut/parse ["1-3 a: abcde" "1-3 b: cdefg" "2-9 c: ccccccccc"]))

(t/deftest validity1
  (t/is (sut/valid1? (data 0)))
  (t/is (not (sut/valid1? (data 1))))
  (t/is (sut/valid1? (data 2))))

(t/deftest count-valid1
  (t/is (= 2 (sut/how-many-valid sut/valid1? data))))

(t/deftest validity2
  (t/is (sut/valid2? (data 0)))
  (t/is (not (sut/valid2? (data 1))))
  (t/is (not (sut/valid2? (data 2)))))

(t/deftest count-valid2
  (t/is (= 1 (sut/how-many-valid sut/valid2? data))))

(t/deftest sut
  (t/is (= 396 sut/part-1))
  (t/is (= 428 sut/part-2)))
