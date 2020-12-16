(ns advent-of-code-2018.day03-test
  (:require [advent-of-code-2018.day03 :as sut]
            [clojure.test :as t]
            [clojure.string :as str]))

(def data-1 (map sut/claim-parser (str/split-lines "#1 @ 1,3: 4x4
#2 @ 3,1: 4x4
#3 @ 5,5: 2x2")))

(t/deftest common-area
  (t/is (= 4 (sut/inches-overlap data-1)))
  (t/is (= 3 (sut/not-overlapping data-1))))

(t/deftest sut
  (t/is (= 118539 sut/part-1))
  (t/is (= 1270 sut/part-2)))
