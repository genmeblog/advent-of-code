(ns advent-of-code-2025.day10-test
  (:require [advent-of-code-2025.day10 :as sut]
            [clojure.test :as t]
            [common :refer [read-data sum]]))

(def data (map sut/parse-line (read-data "examples" 2025 10)))

(t/deftest example
  (t/is (= 7 (sum (map sut/find-minimal-buttons data))))
  (t/is (= 33 (sum (map sut/integer-linear-programming data)))))

(t/deftest sut
  (t/is (= 512 sut/part-1))
  (t/is (= 19857 sut/part-2)))
