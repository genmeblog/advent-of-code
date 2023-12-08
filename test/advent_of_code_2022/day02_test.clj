(ns advent-of-code-2022.day02-test
  (:require [advent-of-code-2022.day02 :as sut]
            [clojure.test :as t]
            [common :refer [read-data]]))

(def data (map sut/parse (read-data "examples" 2022 2)))

(t/deftest examples
  (t/is (= 15 (sut/part-1-calc data)))
  (t/is (= 12 (sut/part-2-calc data))))

(t/deftest sut
  (t/is (= 12276 sut/part-1))
  (t/is (= 9975 sut/part-2)))
