(ns advent-of-code-2024.day11-test
  (:require [advent-of-code-2024.day11 :as sut]
            [clojure.test :as t]
            [common :refer [read-single-line get-numbers]]))

(def data (get-numbers (read-single-line "examples" 2024 11)))

(t/deftest example
  (t/is (= 55312 (sut/stones data 25))))

(t/deftest sut
  (t/is (= 207683 sut/part-1))
  (t/is (= 244782991106220 sut/part-2)))
