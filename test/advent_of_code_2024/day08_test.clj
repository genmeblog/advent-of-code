(ns advent-of-code-2024.day08-test
  (:require [advent-of-code-2024.day08 :as sut]
            [clojure.test :as t]
            [common :refer [read-data]]))

(def data (sut/positions (read-data "examples" 2024 8)))

(t/deftest example
  (t/is (= 14 (sut/all-antinodes sut/antinodes data)))
  (t/is (= 34 (sut/all-antinodes sut/resonants data))))

(t/deftest sut
  (t/is (= 320 sut/part-1))
  (t/is (= 1157 sut/part-2)))
