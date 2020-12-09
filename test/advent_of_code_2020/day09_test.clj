(ns advent-of-code-2020.day09-test
  (:require [advent-of-code-2020.day09 :as sut]
            [clojure.test :as t]
            [clojure.string :as str]))

(def data-1 (map read-string (str/split-lines "35
20
15
25
47
40
62
55
65
95
102
117
150
182
127
219
299
277
309
576")))

(def data-2 (mapv inc (range 25)))
(def data-3 (vec (concat [20] (range 1 20) (range 21 26) [45])))

(t/deftest invalid-value
  (t/is (= 127 (sut/xmas-invalid? data-1 5)))
  (t/is (not (sut/xmas-invalid? (conj data-2 26) 25)))
  (t/is (not (sut/xmas-invalid? (conj data-2 49) 25)))
  (t/is (= 100 (sut/xmas-invalid? (conj data-2 100) 25)))
  (t/is (= 50 (sut/xmas-invalid? (conj data-2 50) 25)))
  (t/is (not (sut/xmas-invalid? (conj data-3 26) 25)))
  (t/is (= 65 (sut/xmas-invalid? (conj data-3 65) 25)))
  (t/is (not (sut/xmas-invalid? (conj data-3 66) 25)))
  (t/is (not (sut/xmas-invalid? (conj data-3 64) 25))))

(t/deftest weakness
  (t/is (= 62 (sut/find-weakness data-1 127))))

(t/deftest sut
  (t/is (= 90433990 sut/part-1))
  (t/is (= 11691646 sut/part-2)))
