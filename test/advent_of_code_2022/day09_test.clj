(ns advent-of-code-2022.day09-test
  (:require [advent-of-code-2022.day09 :as sut]
            [clojure.test :as t]
            [clojure.string :as str]))

(def data2 (mapcat sut/parse (str/split-lines "R 4
U 4
L 3
D 1
R 4
D 1
L 5
R 2")))

(def data3 (mapcat sut/parse (str/split-lines "R 5
U 8
L 8
D 3
R 17
D 10
L 25
U 20")))

(t/deftest examples
  (t/is (= 13 (sut/last-tail-count 1 data2)))
  (t/is (= 1 (sut/last-tail-count 9 data2)))
  (t/is (= 36 (sut/last-tail-count 9 data3))))

(t/deftest sut
  (t/is (= 6522 sut/part-1))
  (t/is (= 2717 sut/part-2)))
