(ns advent-of-code-2020.day19
  (:require [common :refer [read-data-as-blocks]]
            [clojure.string :as str]))

(def data (read-data-as-blocks 2020 19))

(defn build-tree
  [block]
  (let [mem (into {} (map (fn [line]
                            (let [[id r] (-> #"(\d+)\:\s(.*)"
                                             (re-find (str/escape line {\" ""}))
                                             (rest))
                                  ors (str/split r #" \| ")]
                              [id (map #(str/split % #" ")ors)])) block))]    
    mem))

(defn build-regex
  [tree id]
  (let [[[l1 r1] [l2 r2]] (tree id)]
    (str "(:?"
         (if (#{"a" "b"} l1)
           l1
           (str (build-regex tree l1)
                (when r1 (str (build-regex tree r1)))))
         (when (seq l2)
           (str "|" (build-regex tree l2)
                (when r2 (build-regex tree r2))))
         ")")))

(defn regex1
  [data]
  (re-pattern (build-regex (build-tree data) "0")))

(defn regex2
  [data]
  (re-pattern (let [tr (build-tree data)
                    r31 (build-regex tr "31")
                    r42 (build-regex tr "42")]
                (str "(:?" r42 "|" r42 "+)"
                     "(:?" r42 r31 "|" r42 "{2}" r31 "{2}"
                     "|" r42 "{3}" r31 "{3}" "|" r42 "{4}" r31 "{4}"
                     "|" r42 "{5}" r31 "{5}"")"))))

(defn matches
  [data regex]
  (count (filter #(re-matches (regex (first data)) %) (second data))))


(def part-1 (matches data regex1))
;; => 178

(def part-2 (matches data regex2))
;; => 346

(def data2 (common/str-as-blocks "42: 9 14 | 10 1
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



(count (filter #(re-matches regex2 %) (second data)))


(filter #(re-find regex8 %) (second data))

(def part-2 (filter #(do
                       (println %)
                       (re-matches regex2 %)) (filter (complement part-1) (second data))))

(count part-2)

(let [b (build-tree (first data))]
  (b "8"))

(re-matches #"(:?[a-z])+" "zzzaz")
