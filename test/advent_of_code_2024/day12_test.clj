(ns advent-of-code-2024.day12-test
  (:require [advent-of-code-2024.day12 :as sut]
            [clojure.test :as t]
            [common :refer [read-data sum]]))

(def data (sut/separate (read-data "examples" 2024 12)))
(def data1 (sut/separate (read-data "examples" 2024 12 1)))
(def data2 (sut/separate (read-data "examples" 2024 12 2)))
(def data3 (sut/separate (read-data "examples" 2024 12 3)))
(def data4 (sut/separate (read-data "examples" 2024 12 4)))

(t/deftest examples
  (t/are [d v] (= v (sut/fencing-price d sum))
    data 1930 data1 772 data2 140)
  (t/are [d v] (= v (sut/fencing-price d count))
    data 1206 data1 436 data2 80 data3 236 data4 368))

(t/deftest sut
  (t/is (= 1370258 sut/part-1))
  (t/is (= 805814 sut/part-2)))