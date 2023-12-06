(ns advent-of-code-2023.day06-test
  (:require [advent-of-code-2023.day06 :as sut]
            [common :refer [read-data]]
            [clojure.test :as t]))

(def data (sut/parse-data (read-data "examples" 2023 6)))

(t/deftest example
  (t/is (= 288 (sut/solve-races data)))
  (t/is (= 71503 (sut/solve-race data))))

(t/deftest sut
  (t/is (= 1413720 sut/part-1))
  (t/is (= 30565288 sut/part-2)))
