(ns advent-of-code-2020.day14-test
  (:require [advent-of-code-2020.day14 :as sut]
            [clojure.test :as t]
            [clojure.string :as str]))

(def data-1 (str/split-lines "mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
mem[8] = 11
mem[7] = 101
mem[8] = 0"))

(def data-2 (str/split-lines "mask = 000000000000000000000000000000X1001X
mem[42] = 100
mask = 00000000000000000000000000000000X0XX
mem[26] = 1"))

(t/deftest masks
  (t/is (= 165 (sut/memory-sum sut/apply-masks-to-data data-1)))
  (t/is (= 208 (sut/memory-sum sut/apply-masks-to-address data-2))))

(t/deftest sut
  (t/is (= 14839536808842 sut/part-1))
  (t/is (= 4215284199669 sut/part-2)))
