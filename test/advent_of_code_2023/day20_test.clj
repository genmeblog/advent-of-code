(ns advent-of-code-2023.day20-test
  (:require [advent-of-code-2023.day20 :as sut]
            [clojure.test :as t]
            [common :refer [read-data]]))

(def data1 (sut/press-buttons (read-data "examples" 2023 20 1)))
(def data2 (sut/press-buttons (read-data "examples" 2023 20 2)))

(t/deftest examples
  (t/is (= 32000000 (sut/pulses data1)))
  (t/is (= 11687500 (sut/pulses data2))))

(t/deftest sut
  (t/is (= 879834312 sut/part-1))
  (t/is (= 243037165713371 sut/part-2)))
