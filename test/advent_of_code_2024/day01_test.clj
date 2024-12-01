(ns advent-of-code-2024.day01-test
  (:require [advent-of-code-2024.day01 :as sut]
            [common :refer [read-data]]
            [clojure.test :as t]))

(def data (sut/build-lists (read-data "examples" 2024 01)))

(t/deftest example
  (t/is (= 11 (sut/distance data)))
  (t/is (= 31 (sut/similarity data))))

(t/deftest sut
  (t/is (= 1765812 sut/part-1))
  (t/is (= 20520794 sut/part-2)))
