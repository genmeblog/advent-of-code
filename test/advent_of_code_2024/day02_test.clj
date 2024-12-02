(ns advent-of-code-2024.day02-test
  (:require [advent-of-code-2024.day02 :as sut]
            [clojure.test :as t]
            [common :refer [read-data]]))

(def data (map sut/parse-line (read-data "examples" 2024 02)))

(t/deftest example
  (t/is (= 2 (sut/safe-data data)))
  (t/is (= 4 (sut/safe-data-2 data))))

(t/deftest sut
  (t/is (= 269 sut/part-1))
  (t/is (= 337 sut/part-2)))
