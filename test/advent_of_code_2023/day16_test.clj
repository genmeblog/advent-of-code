(ns advent-of-code-2023.day16-test
  (:require [advent-of-code-2023.day16 :as sut]
            [clojure.test :as t]
            [common :refer [read-data]]))

(def data (sut/parse (read-data "examples" 2023 16)))

(t/deftest example
  (t/is (= 46 (sut/move-rays data)))
  (t/is (= 51 (sut/find-max data))))

(t/deftest sut
  (t/is (= 8116 sut/part-1))
  (t/is (= 8383 sut/part-2)))
