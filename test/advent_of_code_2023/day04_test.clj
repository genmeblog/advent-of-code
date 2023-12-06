(ns advent-of-code-2023.day04-test
  (:require [advent-of-code-2023.day04 :as sut]
            [common :refer [read-data]]
            [clojure.test :as t]))

(def data (mapv sut/parse-line (read-data "examples" 2023 4)))

(t/deftest example
  (t/is (= 13 (sut/total-points data)))
  (t/is (= 30 (sut/copies data))))

(t/deftest sut
  (t/is (= 26443 sut/part-1))
  (t/is (= 6284877 sut/part-2)))
