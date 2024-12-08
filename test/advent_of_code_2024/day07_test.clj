(ns advent-of-code-2024.day07-test
  (:require [advent-of-code-2024.day07 :as sut]
            [clojure.test :as t]
            [common :refer [read-data get-numbers]]))

(def data (map get-numbers (read-data "examples" 2024 07)))

(t/deftest example
  (t/is (= 3749 (sut/answer [* +] data)))
  (t/is (= 11387 (sut/answer [* + sut/||] data))))

(t/deftest sut
  (t/is (= 850435817339 sut/part-1))
  (t/is (= 104824810233437 sut/part-2)))
