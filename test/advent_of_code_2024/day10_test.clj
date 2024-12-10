(ns advent-of-code-2024.day10-test
  (:require [advent-of-code-2024.day10 :as sut]
            [clojure.test :as t]
            [common :refer [read-data]]))


(def data  (mapv sut/parse-line (read-data "examples" 2024 10)))
(def data1 (mapv sut/parse-line (read-data "examples" 2024 10 1)))
(def data2 (mapv sut/parse-line (read-data "examples" 2024 10 2)))
(def data3 (mapv sut/parse-line (read-data "examples" 2024 10 3)))
(def data4 (mapv sut/parse-line (read-data "examples" 2024 10 4)))

(def data5 (mapv sut/parse-line (read-data "examples" 2024 10 5)))
(def data6 (mapv sut/parse-line (read-data "examples" 2024 10 6)))
(def data7 (mapv sut/parse-line (read-data "examples" 2024 10 7)))


(t/deftest examples
  (t/are [d v] (= v (sut/traverse d distinct))
    data4 1 data3 2 data2 4 data1 3 data 36)
  (t/are [d v] (= v (sut/traverse d identity))
    data7 3 data6 13 data5 227 data 81))

(t/deftest sut
  (t/is (= 638 sut/part-1))
  (t/is (= 1289 sut/part-2)))
