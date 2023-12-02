(ns advent-of-code-2023.day02-test
  (:require [advent-of-code-2023.day02 :as sut]
            [clojure.test :as t]))

(def data-1 ["Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green"
           "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue"
           "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red"
           "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red"
           "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"])

(t/deftest example
  (t/is (= 8 (sut/id-sums [12 13 14] (sut/parse-data data-1))))
  (t/is (= 2286 (sut/powers-sum (sut/parse-data data-1)))))

(t/deftest sut
  (t/is (= 1931 sut/part-1))
  (t/is (= 83105 sut/part-2)))
