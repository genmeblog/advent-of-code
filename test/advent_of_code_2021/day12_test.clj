(ns advent-of-code-2021.day12-test
  (:require [advent-of-code-2021.day12 :as sut]
            [clojure.test :as t]))

(def data1 (sut/->graph ["start-A"
                       "start-b"
                       "A-c"
                       "A-b"
                       "b-d"
                       "A-end"
                       "b-end"]))

(def data2 (sut/->graph ["dc-end"
                       "HN-start"
                       "start-kj"
                       "dc-start"
                       "dc-HN"
                       "LN-dc"
                       "HN-end"
                       "kj-sa"
                       "kj-HN"
                       "kj-dc"]))

(def data3 (sut/->graph ["fs-end"
                       "he-DX"
                       "fs-he"
                       "start-DX"
                       "pj-DX"
                       "end-zg"
                       "zg-sl"
                       "zg-pj"
                       "pj-he"
                       "RW-he"
                       "fs-DX"
                       "pj-RW"
                       "zg-RW"
                       "start-pj"
                       "he-WI"
                       "zg-he"
                       "pj-fs"
                       "start-RW"]))

(t/deftest part-1-tests
  (t/are [d r] (= r (sut/find-paths d true))
    data1 10
    data2 19
    data3 226))

(t/deftest part-2-tests
  (t/are [d r] (= r (sut/find-paths d false))
    data1 36
    data2 103
    data3 3509))

(t/deftest sut
  (t/is (= 5958 sut/part-1))
  (t/is (= 150426 sut/part-2)))
