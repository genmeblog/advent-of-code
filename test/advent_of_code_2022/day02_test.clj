(ns advent-of-code-2022.day02-test
  (:require [advent-of-code-2022.day02 :as sut]
            [clojure.test :as t]))

(t/deftest examples
  (t/is (= 15 (sut/part-1-calc (map sut/parse ["A Y" "B X" "C Z"]))))
  (t/is (= 12 (sut/part-2-calc (map sut/parse ["A Y" "B X" "C Z"])))))

(t/deftest sut
  (t/is (= 12276 sut/part-1))
  (t/is (= 9975 sut/part-2)))
