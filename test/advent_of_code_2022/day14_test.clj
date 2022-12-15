(ns advent-of-code-2022.day14-test
  (:require [advent-of-code-2022.day14 :as sut]
            [clojure.test :as t]))

(def data (sut/parse-lines ["498,4 -> 498,6 -> 496,6"
                          "503,4 -> 502,4 -> 502,9 -> 494,9"]))

(t/deftest example
  (t/is (= 24 (:counts (sut/till-falling data))))
  (t/is (= 93 (:counts (sut/to-the-top data)))))

(t/deftest sut
  (t/is (= 592 sut/part-1))
  (t/is (= 30367 sut/part-2)))
