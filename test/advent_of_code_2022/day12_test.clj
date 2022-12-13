(ns advent-of-code-2022.day12-test
  (:require [advent-of-code-2022.day12 :as sut]
            [clojure.test :as t]))

(def data2 (sut/parse-data ["Sabqponm"
                          "abcryxxl"
                          "accszExk"
                          "acctuvwj"
                          "abdefghi"]))

(t/deftest example
  (t/is (= 31 (sut/get-path-count data2)))
  (t/is (= 29 (sut/search-minimum-path data2))))

(t/deftest sut
  (t/is (= 534 sut/part-1))
  (t/is (= 525 sut/part-2)))
