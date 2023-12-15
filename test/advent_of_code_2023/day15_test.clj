(ns advent-of-code-2023.day15-test
  (:require [advent-of-code-2023.day15 :as sut]
            [clojure.test :as t]
            [common :refer [read-single-line]]))

(def data (sut/parse (read-single-line "examples" 2023 15)))

(t/deftest examples
  (t/is (= 1320 (reduce + (map sut/HASH data))))
  (t/is (= 145 (sut/total-focusing-power data))))

(t/deftest sut
  (t/is (= 511498 sut/part-1))
  (t/is (= 284674 sut/part-2)))
