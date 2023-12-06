(ns advent-of-code-2023.day06-test
  (:require [advent-of-code-2023.day06 :as sut]
            [clojure.test :as t]))

(def data-1 [["7" "15" "30"]
           ["9" "40" "200"]])

(t/deftest example
  (t/is (= 288 (sut/solve-races data-1)))
  (t/is (= 71503 (sut/solve-race data-1))))

(t/deftest sut
  (t/is (= 1413720 sut/part-1))
  (t/is (= 30565288 sut/part-2)))
