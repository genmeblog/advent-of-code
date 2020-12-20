(ns advent-of-code-2020.day19-test
  (:require [advent-of-code-2020.day19 :as sut]
            [common :refer [str-as-blocks]]
            [clojure.test :as t]
            [clojure.string :as str]))

(def rules-1 (sut/regex1 (str/split-lines "0: 1 2
1: a
2: 1 3 | 3 1
3: b")))

(def data (str-as-blocks "42: 9 14 | 10 1
9: 14 27 | 1 26
10: 23 14 | 28 1
1: a
11: 42 31
5: 1 14 | 15 1
19: 14 1 | 14 14
12: 24 14 | 19 1
16: 15 1 | 14 14
31: 14 17 | 1 13
6: 14 14 | 1 14
2: 1 24 | 14 4
0: 8 11
13: 14 3 | 1 12
15: 1 | 14
17: 14 2 | 1 7
23: 25 1 | 22 14
28: 16 1
4: 1 1
20: 14 14 | 1 15
3: 5 14 | 16 1
27: 1 6 | 14 18
14: b
21: 14 1 | 1 14
25: 1 1 | 1 14
22: 14 14
8: 42
26: 14 22 | 1 20
18: 15 15
7: 14 5 | 1 21
24: 14 1

abbbbbabbbaaaababbaabbbbabababbbabbbbbbabaaaa
bbabbbbaabaabba
babbbbaabbbbbabbbbbbaabaaabaaa
aaabbbbbbaaaabaababaabababbabaaabbababababaaa
bbbbbbbaaaabbbbaaabbabaaa
bbbababbbbaaaaaaaabbababaaababaabab
ababaaaaaabaaab
ababaaaaabbbaba
baabbaaaabbaaaababbaababb
abbbbabbbbaaaababbbbbbaaaababb
aaaaabbaabaaaaababaa
aaaabbaaaabbaaa
aaaabbaabbaaaaaaabbbabbbaaabbaabaaa
babaaabbbaaabaababbaabababaaab
aabbbbbaabbbaaaaaabbbbbababaaaaabbaaabba"))

(t/deftest rules1-matches
  (t/are [s t] (= t (boolean (re-matches rules-1 s)))
    "aaa" false
    "aab" true
    "aba" true
    "abb" false
    "baa" false
    "bab" false
    "bba" false
    "bbb" false))

(t/deftest example-dataset
  (t/is (= 3 (sut/matches data sut/regex1)))
  (t/is (= 12 (sut/matches data sut/regex2))))

(t/deftest sut
  (t/is (= 178 sut/part-1))
  (t/is (= 346 sut/part-2)))
