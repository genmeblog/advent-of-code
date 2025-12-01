(ns advent-of-code-2025.day01-test
  (:require [advent-of-code-2025.day01 :as sut]
            [clojure.test :as t]
            [common :refer [read-data]]))

(def data (map sut/parse-line (read-data "examples" 2025 1)))

(t/deftest example
  (t/is (= 3 (sut/find-zeros data sut/first-method)))
  (t/is (= 6 (sut/find-zeros data sut/second-method))))

(t/deftest sut
  (t/is (= 1097 sut/part-1))
  (t/is (= 7101 sut/part-2)))
