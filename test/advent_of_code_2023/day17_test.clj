(ns advent-of-code-2023.day17-test
  (:require [advent-of-code-2023.day17 :as sut]
            [clojure.test :as t]
            [common :refer [read-data]]))

(def data1 (mapv sut/parse-line (read-data "examples" 2023 17)))
(def data2 (mapv sut/parse-line (read-data "examples" 2023 17 1)))

(t/deftest examples
  (t/is (= 102 (sut/find-path data1 1 3)))
  (t/is (= 94 (sut/find-path data1 4 10)))
  (t/is (= 71 (sut/find-path data2 4 10))))

(t/deftest sut
  (t/is (= 1013 sut/part-1))
  (t/is (= 1215 sut/part-2)))
