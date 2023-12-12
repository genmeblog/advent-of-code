(ns advent-of-code-2023.day12-test
  (:require [advent-of-code-2023.day12 :as sut]
            [clojure.test :as t]
            [common :refer [read-data]]))

(def data (map sut/parse-line (read-data "examples" 2023 12)))

(t/deftest example
  (t/is (= 21 (sut/counts data)))
  (t/is (= 525152 (sut/counts data sut/repeat-rule))))

(t/deftest sut
  (t/is (= 6958 sut/part-1))
  (t/is (= 6555315065024 sut/part-2)))
