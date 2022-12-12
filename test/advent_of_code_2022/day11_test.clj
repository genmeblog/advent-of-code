(ns advent-of-code-2022.day11-test
  (:require [advent-of-code-2022.day11 :as sut]
            [common :refer [str-as-blocks]]
            [clojure.test :as t]))

(def data (mapv sut/parse (str-as-blocks "Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3

Monkey 1:
  Starting items: 54, 65, 75, 74
  Operation: new = old + 6
  Test: divisible by 19
    If true: throw to monkey 2
    If false: throw to monkey 0

Monkey 2:
  Starting items: 79, 60, 97
  Operation: new = old * old
  Test: divisible by 13
    If true: throw to monkey 1
    If false: throw to monkey 3

Monkey 3:
  Starting items: 74
  Operation: new = old + 3
  Test: divisible by 17
    If true: throw to monkey 0
    If false: throw to monkey 1")))

(t/deftest example
  (t/is (= 10605 (sut/most-inspected1 data)))
  (t/is (= 2713310158 (sut/most-inspected2 data))))

(t/deftest sut
  (t/is (= 110888 sut/part-1))
  (t/is (= 25590400731 sut/part-2)))
