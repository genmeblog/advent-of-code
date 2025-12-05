(ns advent-of-code-2025.day04-test
  (:require [advent-of-code-2025.day04 :as sut]
            [clojure.test :as t]
            [common :refer [read-data]]))


(def data (sut/count-all (read-data "examples" 2025 4)))

(t/deftest example
  (t/is (= 13 (first data)))
  (t/is (= 43 (reduce + data))))

(t/deftest sut
  (t/is (= 1363 sut/part-1))
  (t/is (= 8184 sut/part-2)))
