(ns advent-of-code-2021.day02-test
  (:require [advent-of-code-2021.day02 :as sut]
            [common :refer [parse]]
            [clojure.test :as t]))

(def data (->> ["forward 5"
                "down 5"
                "forward 8"
                "up 3"
                "down 8"
                "forward 2"]
               (map (partial parse sut/re))))

(t/deftest test-data
  (t/is (= 150 (sut/process data)))
  (t/is (= 900 (sut/process-aim data))))

(t/deftest sut
  (t/is (= 1947824 sut/part-1))
  (t/is (= 1813062561 sut/part-2)))
