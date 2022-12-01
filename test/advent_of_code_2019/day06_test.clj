(ns advent-of-code-2019.day06-test
  (:require [advent-of-code-2019.day06 :as sut]
            [common :refer [split-line]]
            [clojure.test :as t]))

(def data1 (sut/parse (split-line "COM)B
B)C
C)D
D)E
E)F
B)G
G)H
D)I
E)J
J)K
K)L")))

(def data2 (sut/parse (split-line "COM)B
B)C
C)D
D)E
E)F
B)G
G)H
D)I
E)J
J)K
K)L
K)YOU
I)SAN")))

(t/deftest examples
  (t/is (= 42 (sut/all-paths data1)))
  (t/is (= 4 (sut/minimal-path data2))))

(t/deftest sut
  (t/is (= 135690 sut/part-1))
  (t/is (= 298 sut/part-2)))
