(ns advent-of-code-2020.day10-test
  (:require [advent-of-code-2020.day10 :as sut]
            [clojure.test :as t]
            [clojure.string :as str]))

(def data-1 (sort > (conj (map read-string (str/split-lines "16
10
15
5
1
11
7
19
6
12
4")) 0)))

(def data-2 (sort > (conj (map read-string (str/split-lines "28
33
18
42
31
14
46
20
48
47
24
23
49
45
19
38
39
11
1
32
25
35
8
17
7
9
4
2
34
10
3")) 0)))

(t/deftest jolts
  (t/is (= 35 (sut/jolts data-1)))
  (t/is (= 220 (sut/jolts data-2))))

(t/deftest arrgments
  (t/is (= 8 (sut/arrangements data-1)))
  (t/is (= 19208 (sut/arrangements data-2))))

(t/deftest sut
  (t/is (= 2592 sut/part-1))
  (t/is (= 198428693313536 sut/part-2)))
