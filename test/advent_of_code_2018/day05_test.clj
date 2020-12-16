(ns advent-of-code-2018.day05-test
  (:require [advent-of-code-2018.day05 :as sut]
            [clojure.test :as t]))

(def data (map int "dabAcCaCBAcCcaDA"))

(t/deftest sample
  (t/is (= 10 (count (sut/react data))))
  (t/is (= 4 (reduce min (sut/removed-unit-reactions data)))))

(t/deftest sut
  (t/is (= 11540 sut/part-1))
  (t/is (= 6918 sut/part-2)))
