(ns advent-of-code-2022.day06-test
  (:require [advent-of-code-2022.day06 :as sut]
            [clojure.test :as t]))

(t/deftest examples
  (t/are [message id1 id2] (and (= id1 (sut/marker-id message 4))
                                (= id2 (sut/marker-id message 14)))
    "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw" 11 26
    "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" 10 29
    "nppdvjthqldpwncqszvftbrmjlhg" 6 23
    "bvwbjplbgvbhsrlpgdmjqwftvncz" 5 23
    "mjqjpqmgbljsphdztnvjfqwrcgsmlb" 7 19))

(t/deftest sut
  (t/is (= 1238 sut/part-1))
  (t/is (= 3037 sut/part-2)))
