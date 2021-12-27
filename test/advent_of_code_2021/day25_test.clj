(ns advent-of-code-2021.day25-test
  (:require [advent-of-code-2021.day25 :as sut]
            [clojure.test :as t]))

(def data (sut/parse ["v...>>.vv>"
                    ".vv>>.vv.."
                    ">>.>v>...v"
                    ">>v>>.>.v."
                    "v>v.vv.v.."
                    ">.>>..v..."
                    ".vv..>.>v."
                    "v.v..>>v.v"
                    "....v..v.>"]))

(t/deftest test-data
  (t/is (= 58 (sut/move-until-stop data))))

(t/deftest sut
  (t/is (= 458 sut/part-1)))
