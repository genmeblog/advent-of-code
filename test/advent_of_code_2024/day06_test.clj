(ns advent-of-code-2024.day06-test
  (:require [advent-of-code-2024.day06 :as sut]
            [clojure.test :as t]
            [common :refer [read-data]]))

(def data (sut/parse-data (read-data "examples" 2024 06)))

(t/deftest example
  (t/is (= 41 (count (sut/visited data))))
  (t/is (= 6 (sut/with-obstacles data))))

(t/deftest sut
  (t/is (= 5305 sut/part-1))
  (t/is (= 2143 sut/part-2)))
