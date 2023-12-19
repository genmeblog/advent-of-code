(ns advent-of-code-2023.day18-test
  (:require [advent-of-code-2023.day18 :as sut]
            [clojure.test :as t]
            [common :refer [read-data]]))

(def data (map sut/parse-line (read-data "examples" 2023 18)))
(def data-1 [[:R 12] [:D 14] [:L 2] [:U 12] [:L 2] [:D 10] [:L 2] [:U 8] [:L 2] [:D 6] [:L 2] [:U 4]
           [:L 2] [:U 2] [:R 2] [:U 2] [:L 2] [:U 2]])

(t/deftest examples
  (t/is (= 137 (sut/volume data-1)))
  (t/is (= 62 (sut/volume data)))
  (t/is (= 952408144115 (sut/volume data true))))

(t/deftest sut
  (t/is (= 40745 sut/part-1))
  (t/is (= 90111113594927 sut/part-2)))
