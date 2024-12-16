(ns advent-of-code-2024.day16-test
  (:require [advent-of-code-2024.day16 :as sut]
            [clojure.test :as t]
            [common :refer [read-data]]))

(def data (sut/->data (read-data "examples" 2024 16)))
(def data1 (sut/->data (read-data "examples" 2024 16 1)))

(t/deftest examples
  (t/is (= 7036 (sut/find-best data)))
  (t/is (= 11048 (sut/find-best data1)))
  (t/is (= 45 (sut/find-comfortable data)))
  (t/is (= 64 (sut/find-comfortable data1))))

(t/deftest sut
  (t/is (= 135536 sut/part-1))
  (t/is (= 583 sut/part-2)))
