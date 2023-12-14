(ns advent-of-code-2023.day14-test
  (:require [advent-of-code-2023.day14 :as sut]
            [clojure.test :as t]
            [common :refer [read-data]]))

(def data (apply map str (read-data "examples" 2023 14)))

(t/deftest example
  (t/is (= 136 (sut/score-all (sut/tilt-all data))))
  (t/is (= 64 (sut/score-all (sut/nth-tilt data 1000000000)))))

(t/deftest sut
  (t/is (= 109833 sut/part-1))
  (t/is (= 99875 sut/part-2)))
