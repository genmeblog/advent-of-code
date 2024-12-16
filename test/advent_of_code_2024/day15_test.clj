(ns advent-of-code-2024.day15-test
  (:require [advent-of-code-2024.day15 :as sut]
            [clojure.test :as t]
            [common :refer [read-data-as-blocks]]))

(def data (sut/parse-data (read-data-as-blocks "examples" 2024 15)))
(def data1 (sut/parse-data (read-data-as-blocks "examples" 2024 15 1)))
(def data2 (sut/parse-data (read-data-as-blocks "examples" 2024 15 2)))

(t/deftest examples
  (t/are [d v] (= v (sut/move-all d)) data 10092 data1 2028)
  (t/is (= 9021 (sut/move-all-2 data))))

(t/deftest sut
  (t/is (= 1476771 sut/part-1))
  (t/is (= 1468005 sut/part-2)))
