(ns advent-of-code-2020.day25-test
  (:require [advent-of-code-2020.day25 :as sut]
            [clojure.test :as t]))

(t/deftest example
  (t/is (= 14897079 (nth (sut/steps 5764801) (sut/loop-number 17807724)))))

(t/deftest sut
  (t/is (= 4126980 sut/part-1)))
