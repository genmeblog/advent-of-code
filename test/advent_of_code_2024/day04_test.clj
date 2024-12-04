(ns advent-of-code-2024.day04-test
  (:require [advent-of-code-2024.day04 :as sut]
            [clojure.test :as t]
            [common :refer [read-data]]))

(def data1 (mapv vec (read-data "examples" 2024 04)))
(def data2 (mapv vec (read-data "examples" 2024 04 1)))

(t/deftest examples
  (t/is (= 18 (sut/count-patterns data1 sut/xmas-offsets sut/xmas-patterns)))
  (t/is (= 4 (sut/count-patterns data2 sut/xmas-offsets sut/xmas-patterns)))
  (t/is (= 9 (sut/count-patterns data1 sut/x-mas-offsets sut/x-mas-patterns))))

(t/deftest sut
  (t/is (= 2642 sut/part-1))
  (t/is (= 1974 sut/part-2)))
