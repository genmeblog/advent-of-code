(ns advent-of-code-2022.day04-test
  (:require [advent-of-code-2022.day04 :as sut]
            [clojure.test :as t]))

(def data (map sut/parse ["2-4,6-8"
                        "2-3,4-5"
                        "5-7,7-9"
                        "2-8,3-7"
                        "6-6,4-6"
                        "2-6,4-8"]))

(t/deftest example
  (t/is (= 2 (count (filter sut/include? data))))
  (t/is (= 4 (count (filter sut/overlap? data)))))

(t/deftest sut
  (t/is (= 562 sut/part-1))
  (t/is (= 924 sut/part-2)))
