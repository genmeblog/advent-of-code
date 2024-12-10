(ns advent-of-code-2024.day09-test
  (:require [advent-of-code-2024.day09 :as sut]
            [clojure.test :as t]))

(def data (sut/produce-lists "2333133121414131402"))

(t/deftest example
  (t/is (= 1928 (sut/move-to-gap data)))
  (t/is (= 2858 (sut/fit-in-gap data))))

(t/deftest sut
  (t/is (= 6242766523059 sut/part-1))
  (t/is (= 6272188244509 sut/part-2)))
