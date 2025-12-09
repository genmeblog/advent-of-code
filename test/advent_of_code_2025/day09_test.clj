(ns advent-of-code-2025.day09-test
  (:require [advent-of-code-2025.day09 :as sut]
            [clojure.test :as t]
            [common :refer [read-data get-numbers]]))

(def data (map get-numbers (read-data "examples" 2025 9)))

(t/deftest example
  (t/is (= 50 (sut/max-rectangle data)))
  (t/is (= 24 (sut/max-rectangle-on-rg data))))

(t/deftest sut
  (t/is (= 4774877510 sut/part-1))
  (t/is (= 1560475800 sut/part-2)))
