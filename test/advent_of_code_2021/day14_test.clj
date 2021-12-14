(ns advent-of-code-2021.day14-test
  (:require [advent-of-code-2021.day14 :as sut]
            [clojure.test :as t]
            [common]))

(def data (sut/parser (common/str-as-blocks "NNCB

CH -> B
HH -> N
CB -> H
NH -> C
HB -> C
HC -> B
HN -> C
NN -> C
BH -> H
NC -> B
NB -> B
BN -> B
BB -> N
BC -> B
CC -> N
CN -> C")))

(t/deftest test-data
  (t/is (= 1588 (sut/nth-polymer-data data 10)))
  (t/is (= 2188189693529 (sut/nth-polymer-data data 40))))

(t/deftest sut
  (t/is (= 2194 sut/part-1))
  (t/is (= 2360298895777 sut/part-2)))
