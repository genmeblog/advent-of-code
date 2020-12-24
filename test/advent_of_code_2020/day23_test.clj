(ns advent-of-code-2020.day23-test
  (:require [advent-of-code-2020.day23 :as sut]
            [clojure.test :as t]))

(t/deftest other-game
  (t/is (= 67384529 (sut/game-1 [3 8 9 1 2 5 4 6 7])))
  (t/is (= 149245887792 (sut/game-2 [3 8 9 1 2 5 4 6 7]))))

(t/deftest sut
  (t/is (= 24798635 sut/part-1))
  (t/is (= 12757828710 sut/part-2)))
