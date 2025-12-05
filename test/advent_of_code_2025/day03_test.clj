(ns advent-of-code-2025.day03-test
  (:require [advent-of-code-2025.day03 :as sut]
            [clojure.test :as t]
            [common :refer [read-data]]))

(def data (map sut/parse (read-data "examples" 2025 3)))

(t/deftest example
  (t/is (= 357 (sut/total-joltage 2 data)))
  (t/is (= 3121910778619 (sut/total-joltage 12 data))))

(t/deftest sut
  (t/is (= 17229 sut/part-1))
  (t/is (= 170520923035051 sut/part-2)))
