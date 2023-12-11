(ns advent-of-code-2023.day11-test
  (:require [advent-of-code-2023.day11 :as sut]
            [clojure.test :as t]
            [common :refer [read-data]]))

(def data (sut/parse-data (vec (read-data "examples" 2023 11))))

(t/deftest example
  (t/is (= 374 (sut/distances data 1)))
  (t/is (= 1030 (sut/distances data 9)))
  (t/is (= 8410 (sut/distances data 99))))

(t/deftest sut
  (t/is (= 9686930 sut/part-1))
  (t/is (= 630728425490 sut/part-2)))
