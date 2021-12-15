(ns advent-of-code-2021.day15-test
  (:require [advent-of-code-2021.day15 :as sut]
            [clojure.test :as t]))

(def data ["1163751742"
         "1381373672"
         "2136511328"
         "3694931569"
         "7463417111"
         "1319128137"
         "1359912421"
         "3125421639"
         "1293138521"
         "2311944581"])

(t/deftest test-data
  (t/is (= 40 (sut/dijkstra (sut/parser data))))
  (t/is (= 315 (sut/dijkstra (sut/parser data 5)))))

(t/deftest sut
  (t/is (= 553 sut/part-1))
  (t/is (= 2858 sut/part-2)))
