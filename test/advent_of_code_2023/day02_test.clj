(ns advent-of-code-2023.day02-test
  (:require [advent-of-code-2023.day02 :as sut]
            [common :refer [read-data]]
            [clojure.test :as t]))

(def data (read-data "examples" 2023 2))

(t/deftest example
  (t/is (= 8 (sut/id-sums [12 13 14] (sut/parse-data data))))
  (t/is (= 2286 (sut/powers-sum (sut/parse-data data)))))

(t/deftest sut
  (t/is (= 1931 sut/part-1))
  (t/is (= 83105 sut/part-2)))
