(ns advent-of-code-2020.day22-test
  (:require [advent-of-code-2020.day22 :as sut]
            [clojure.test :as t]
            [common :refer [str-as-blocks]]))

(def data (mapv sut/parser (str-as-blocks "Player 1:
9
2
6
3
1

Player 2:
5
8
4
7
10")))

(t/deftest example-games
  (t/is (= 306 (sut/game (sut/find-winner sut/winning-regular data))))
  (t/is (= 291 (sut/game (sut/find-winner sut/winning-recursive data)))))

(t/deftest sut
  (t/is (= 31629 sut/part-1))
  (t/is (= 35196 sut/part-2)))
