(ns advent-of-code-2020.day16-test
  (:require [advent-of-code-2020.day16 :as sut]
            [common :refer [str-as-blocks]]
            [clojure.test :as t]))

(def data-1 (str-as-blocks "class: 1-3 or 5-7
row: 6-11 or 33-44
seat: 13-40 or 45-50

your ticket:
7,1,14

nearby tickets:
7,3,47
40,4,50
55,2,20
38,6,12"))

(def data-2 (str-as-blocks "class: 0-1 or 4-19
row: 0-5 or 8-19
seat: 0-13 or 16-19

your ticket:
11,12,13

nearby tickets:
3,9,18
15,1,5
5,14,9"))

(t/deftest sum-of-invalid
  (t/is (= 71 (sut/find-invalid data-1))))

(t/deftest fields
  (let [idxs (into {} (sut/find-indexes data-2))
        my-ticket (vec (first (sut/parse-tickets (second data-2))))]
    (t/is (= 11 (my-ticket (idxs "row"))))
    (t/is (= 12 (my-ticket (idxs "class"))))
    (t/is (= 13 (my-ticket (idxs "seat"))))))

(t/deftest sut
  (t/is (= 23954 sut/part-1))
  (t/is (= 453459307723 sut/part-2)))
