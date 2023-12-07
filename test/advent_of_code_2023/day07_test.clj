(ns advent-of-code-2023.day07-test
  (:require [advent-of-code-2023.day07 :as sut]
            [clojure.test :as t]
            [common :refer [read-data]]))

(def data (read-data "examples" 2023 7))

(t/deftest examples
  (t/is (= 6440 (sut/score-game data sut/escapes-part1 sut/score-type)))
  (t/is (= 5905 (sut/score-game data sut/escapes-part2 sut/score-best))))

(t/deftest sut
  (t/is (= 253910319 sut/part-1))
  (t/is (= 254083736 sut/part-2)))
