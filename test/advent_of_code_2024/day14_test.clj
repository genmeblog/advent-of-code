(ns advent-of-code-2024.day14-test
  (:require [advent-of-code-2024.day14 :as sut]
            [clojure.test :as t]
            [common :refer [read-data]]))

(def data (apply map vector (map sut/parse (read-data "examples" 2024 14))))

(t/deftest example
  (t/is (= 12 (sut/count-by-quadrants data 11 7))))

(t/deftest sut
  (t/is (= 224357412 sut/part-1))
  (t/is (= 7083 sut/part-2)))
