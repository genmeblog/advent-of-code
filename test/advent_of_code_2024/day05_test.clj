(ns advent-of-code-2024.day05-test
  (:require [advent-of-code-2024.day05 :as sut]
            [clojure.test :as t]
            [common :refer [read-data-as-blocks]]))

(def data (sut/parse-data (read-data-as-blocks "examples" 2024 05)))

(t/deftest example
  (t/is (= 143 (sut/sum-of-middles data true?)))
  (t/is (= 123 (sut/sum-of-middles data false?))))

(t/deftest sut
  (t/is (= 4790 sut/part-1))
  (t/is (= 6319 sut/part-2)))
