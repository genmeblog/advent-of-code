(ns advent-of-code-2022.day03-test
  (:require [advent-of-code-2022.day03 :as sut]
            [clojure.test :as t]))

(def data (map sut/parse ["vJrwpWtwJgWrhcsFMMfFFhFp"
                        "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL"
                        "PmmdzqPrVvPwwTWBwg"
                        "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn"
                        "ttgJtRGJQctTZtZT"
                        "CrZsJsPPZsGzwwsLwLmpwMDw"]))

(t/deftest example
  (t/is (= 157 (sut/points (map sut/split data))))
  (t/is (= 70 (sut/points (partition 3 data)))))

(t/deftest sut
  (t/is (= 7821 sut/part-1))
  (t/is (= 2752 sut/part-2)))
