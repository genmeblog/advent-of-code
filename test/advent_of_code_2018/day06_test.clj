(ns advent-of-code-2018.day06-test
  (:require [advent-of-code-2018.day06 :as sut]
            [clojure.test :as t]
            [clojure.string :as str]))

(def data (str/split-lines "1, 1
1, 6
8, 3
3, 4
5, 5
8, 9"))

(t/deftest simple-voronoi
  (t/is (= 17 (sut/maximum data)))
  (t/is (= 16 (sut/region data 32))))

(sut/dist-seq data)

(t/deftest sut
  (t/is (= 4186 sut/part-1))
  (t/is (= 45509 sut/part-2)))


