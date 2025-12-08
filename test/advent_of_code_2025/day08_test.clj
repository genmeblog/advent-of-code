(ns advent-of-code-2025.day08-test
  (:require [advent-of-code-2025.day08 :as sut]
            [clojure.test :as t]
            [common :refer [read-data]]))

(def data (sut/order-boxes (read-data "examples" 2025 8)))

(t/deftest example
  (t/is (= 40 (sut/n-shortests data 10)))
  (t/is (= 25272 (sut/process-pairs (:size data) (:ordered data)))))

(t/deftest sut
  (t/is (= 68112 sut/part-1))
  (t/is (= 44543856 sut/part-2)))
