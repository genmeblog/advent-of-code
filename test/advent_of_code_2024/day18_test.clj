(ns advent-of-code-2024.day18-test
  (:require [advent-of-code-2024.day18 :as sut]
            [clojure.test :as t]
            [common :refer [get-numbers read-data]]))

(def data (mapv get-numbers (read-data "examples" 2024 18)))

(t/deftest example
  (t/is (= 22 (sut/traverse data 6 12)))
  (t/is (= "6,1" (sut/find-blocking data 6))))

(t/deftest sut
  (t/is (= 292 sut/part-1))
  (t/is (= "58,44" sut/part-2)))
