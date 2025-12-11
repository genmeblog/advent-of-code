(ns advent-of-code-2025.day11-test
  (:require [advent-of-code-2025.day11 :as sut]
            [clojure.test :as t]
            [common :refer [read-data]]))

(def data (sut/parse (read-data "examples" 2025 11)))
(def data1 (sut/parse (read-data "examples" 2025 11 1)))

(t/deftest examples
  (t/is (= 5 (sut/count-paths data :you :none)))
  (t/is (= 2 (sut/count-paths data1 :svr :both))))

(t/deftest sut
  (t/is (= 497 sut/part-1))
  (t/is (= 358564784931864 sut/part-2)))
