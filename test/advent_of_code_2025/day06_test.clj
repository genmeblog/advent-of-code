(ns advent-of-code-2025.day06-test
  (:require [advent-of-code-2025.day06 :as sut]
            [clojure.test :as t]
            [common :refer [read-data]]))

(def data (read-data "examples" 2025 6))

(t/deftest example
  (t/is (= 4277556 (sut/math-1 data)))
  (t/is (= 3263827 (sut/math-2 data))))

(t/deftest sut
  (t/is (= 6957525317641 sut/part-1))
  (t/is (= 13215665360076 sut/part-2)))
