(ns advent-of-code-2023.day09-test
  (:require [advent-of-code-2023.day09 :as sut]
            [clojure.test :as t]
            [common :refer [read-data]]))

(def data (map sut/parse-lines (read-data "examples" 2023 9)))

(t/deftest example
  (t/is (= 114 (sut/extrapolated-values data true)))
  (t/is (= 2 (sut/extrapolated-values data false))))

(t/deftest sut
  (t/is (= 1930746032 sut/part-1))
  (t/is (= 1154 sut/part-2)))
