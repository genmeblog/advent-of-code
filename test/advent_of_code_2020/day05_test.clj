(ns advent-of-code-2020.day05-test
  (:require [advent-of-code-2020.day05 :as sut]
            [clojure.test :as t]))

(t/deftest conversion
  (t/are [s res] (= res (sut/->number s))
    "FBFBBFFRLR" 357
    "BFFFBBFRRR" 567
    "FFFBBBFRRR" 119
    "BBFFBBFRLL" 820))
