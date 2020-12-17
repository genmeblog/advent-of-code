(ns advent-of-code-2020.day17-test
  (:require [advent-of-code-2020.day17 :as sut]
            [clojure.test :as t]
            [clojure.string :as str]))

(def data (sut/parse (str/split-lines ".#.
..#
###")))

(t/deftest cube-counts
  (t/is (= 112 (sut/cubes data false)))
  (t/is (= 848 (sut/cubes data true))))

(t/deftest sut
  (t/is (= 269 sut/part-1))
  (t/is (= 1380 sut/part-2)))
