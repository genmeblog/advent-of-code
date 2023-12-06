(ns advent-of-code-2023.day03-test
  (:require [advent-of-code-2023.day03 :as sut]
            [common :refer [read-data]]
            [clojure.test :as t]))

(def data (sut/parse-data (read-data "examples" 2023 3)))

(t/deftest example
  (t/is (= 4361 (sut/part-numbers data)))
  (t/is (= 467835 (sut/gears-ratios data))))

(t/deftest sut
  (t/is (= 520135 sut/part-1))
  (t/is (= 72514855 sut/part-2)))
