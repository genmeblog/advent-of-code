(ns advent-of-code-2022.day08-test
  (:require [advent-of-code-2022.day08 :as sut]
            [clojure.test :as t]))


(def data (sut/build-data ["30373"
                         "25512"
                         "65332"
                         "33549"
                         "35390"]))

(t/deftest example
  (t/is (= 21 (count (sut/check-visibility sut/visible? + data))))
  (t/is (= 8 (reduce max (sut/check-visibility sut/visible-trees * data)))))

(t/deftest sut
  (t/is (= 1681 sut/part-1))
  (t/is (= 201684 sut/part-2)))
