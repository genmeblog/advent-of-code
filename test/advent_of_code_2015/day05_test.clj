(ns advent-of-code-2015.day05-test
  (:require [advent-of-code-2015.day05 :as sut]
            [clojure.test :as t]))

(t/deftest preds
  (t/is (sut/three-vowels? "aei"))
  (t/is (sut/three-vowels? "xazegov"))
  (t/is (sut/three-vowels? "aeiouaeiouaeiou"))
  (t/is (sut/double-letter? (partition 2 1 "abcdde")))
  (t/is (sut/double-letter? (partition 2 1 "aabbccdd")))
  (t/is (sut/double-doubles? "xyxy"))
  (t/is (sut/double-doubles? "aabcdefgaa"))
  (t/is (not (sut/double-doubles? "aaa")))
  (t/is (sut/letter-between? "xyx"))
  (t/is (sut/letter-between? "abcdefeghi"))
  (t/is (sut/letter-between? "aaa")))

(t/deftest nicenests
  (t/is (sut/nice? "ugknbfddgicrmopn"))
  (t/is (sut/nice? "aaa"))
  (t/is (not (sut/nice? "jchzalrnumimnmhp")))
  (t/is (not (sut/nice? "haegwjzuvuyypxyu")))
  (t/is (not (sut/nice? "dvszwmarrgswjxmb"))))

(t/deftest better-nicenests
  (t/is (sut/better-nice? "qjhvhtzxzqqjkmpb"))
  (t/is (sut/better-nice? "xxyxx"))
  (t/is (not (sut/better-nice? "uurcxstgmygtbstg")))
  (t/is (not (sut/better-nice? "ieodomkazucvgmuy"))))

(t/deftest sut
  (t/is (= 238 sut/part-1))
  (t/is (= 69 sut/part-2)))
