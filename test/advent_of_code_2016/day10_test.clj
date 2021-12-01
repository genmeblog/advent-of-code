(ns advent-of-code-2016.day10-test
  (:require [advent-of-code-2016.day10 :as sut]
            [clojure.test :as t]))

(def data (->> ["value 5 goes to bot 2"
                "bot 2 gives low to bot 1 and high to bot 0"
                "value 3 goes to bot 1"
                "bot 1 gives low to output 1 and high to bot 0"
                "bot 0 gives low to output 2 and high to output 0"
                "value 2 goes to bot 2"]
               (sut/parser)
               (iterate sut/move)))

(t/deftest stop-test
  (t/is (= 30 (sut/outputs123 data))))

(t/deftest sut
  (t/is (= 113 sut/part-1))
  (t/is (= 12803 sut/part-2)))
