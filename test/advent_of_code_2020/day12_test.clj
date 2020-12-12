(ns advent-of-code-2020.day12-test
  (:require [advent-of-code-2020.day12 :as sut]
            [clojure.test :as t]
            [clojure.string :as str]))

(def data (map sut/parser (str/split-lines "F10
N3
F7
R90
F11")))

(t/deftest simple-data
  (t/is (= 25 (sut/manhattan-distance sut/make-step data [0 0 90])))
  (t/is (= 286 (sut/manhattan-distance sut/make-waypoint-step data [0 0 10 -1]))))

(t/deftest sut
  (t/is (= 1601 sut/part-1))
  (t/is (= 13340 sut/part-2)))
