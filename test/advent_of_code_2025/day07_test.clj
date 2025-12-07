(ns advent-of-code-2025.day07-test
  (:require [advent-of-code-2025.day07 :as sut]
            [clojure.test :as t]
            [common :refer [read-data sum]]))

(def data (sut/parse-and-calculate (read-data "examples" 2025 7)))

(t/deftest example
  (t/is (= 21 (-> data second)))
  (t/is (= 40 (-> data first vals sum))))

(t/deftest sut
  (t/is (= 1600 sut/part-1))
  (t/is (= 8632253783011 sut/part-2)))
