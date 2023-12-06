(ns advent-of-code-2023.day01-test
  (:require [advent-of-code-2023.day01 :as sut]
            [common :refer [read-data]]
            [clojure.test :as t]))

(t/deftest examples
  (t/is (= 142 (sut/part-1-solution (read-data "examples" 2023 1 1))))
  (t/is (= 281 (sut/part-2-solution (read-data "examples" 2023 1 2)))))

(t/deftest sut
  (t/is (= 55029 sut/part-1))
  (t/is (= 55686 sut/part-2)))
