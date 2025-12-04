(ns advent-of-code-2025.day02-test
  (:require [advent-of-code-2025.day02 :as sut]
            [clojure.test :as t]
            [common :refer [read-single-line]]))

(def data (sut/parse-line (read-single-line "examples" 2025 2)))

(t/deftest example
  (t/is (= 1227775554 (sut/all-invalid data sut/check-half)))
  (t/is (= 4174379265 (sut/all-invalid data sut/check-number))))

(t/deftest sut
  (t/is (= 23534117921 sut/part-1))
  (t/is (= 31755323497 sut/part-2)))
