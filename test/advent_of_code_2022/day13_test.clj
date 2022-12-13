(ns advent-of-code-2022.day13-test
  (:require [advent-of-code-2022.day13 :as sut]
            [clojure.test :as t]))

(def data [[[1,1,3,1,1]
          [1,1,5,1,1]]

         [[[1],[2,3,4]]
          [[1],4]]

         [[9]
          [[8,7,6]]]

         [[[4,4],4,4]
          [[4,4],4,4,4]]

         [[7,7,7,7]
          [7,7,7]]

         [[]
          [3]]

         [[[[]]]
          [[]]]

         [[1,[2,[3,[4,[5,6,7]]]],8,9]
          [1,[2,[3,[4,[5,6,0]]]],8,9]]])

(t/deftest example
  (t/is (= -1 (sut/compare-pairs [1 1 3 1 1] [1 1 5 1 1])))
  (t/is (= -1 (sut/compare-pairs [[1] [2 3 4]] [[1] 4])))
  (t/is (=  1 (sut/compare-pairs [9] [[8 7 6]])))
  (t/is (= -1 (sut/compare-pairs [[4,4],4,4] [[4,4],4,4,4])))
  (t/is (=  1 (sut/compare-pairs [7,7,7,7] [7,7,7])))
  (t/is (= -1 (sut/compare-pairs [] [3])))
  (t/is (=  1 (sut/compare-pairs [1,[2,[3,[4,[5,6,7]]]],8,9] [1,[2,[3,[4,[5,6,0]]]],8,9])))
  (t/is (= 13 (sut/ordered-pairs-score data)))
  (t/is (= 140 (sut/decoder-key data))))

(t/deftest sut
  (t/is (= 5852 sut/part-1))
  (t/is (= 24190 sut/part-2)))
