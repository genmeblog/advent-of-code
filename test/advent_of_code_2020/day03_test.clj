(ns advent-of-code-2020.day03-test
  (:require [advent-of-code-2020.day03 :as sut]
            [clojure.test :as t]
            [clojure.string :as str]))

(def data (str/split-lines "..##.......
#...#...#..
.#....#..#.
..#.#...#.#
.#...##..#.
..#.##.....
.#.#.#....#
.#........#
#.##...#...
#...##....#
.#..#...#.#"))

(t/deftest tree-counts
  (t/are [cnt in] (= cnt (sut/tree-count data in))
    7 [3 1]
    2 [1 1]
    3 [5 1]
    4 [7 1]
    2 [1 2]))

(t/deftest multiply
  (t/is (= 336 (sut/multiply-trees data sut/slopes))))
