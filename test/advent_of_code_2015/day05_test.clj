(ns advent-of-code-2015.day05-test
  (:require [advent-of-code-2015.day05 :refer :all]
            [clojure.test :refer :all]))

(deftest preds
  (is (three-vowels? "aei"))
  (is (three-vowels? "xazegov"))
  (is (three-vowels? "aeiouaeiouaeiou"))
  (is (double-letter? (partition 2 1 "abcdde")))
  (is (double-letter? (partition 2 1 "aabbccdd")))
  (is (double-doubles? "xyxy"))
  (is (double-doubles? "aabcdefgaa"))
  (is (not (double-doubles? "aaa")))
  (is (letter-between? "xyx"))
  (is (letter-between? "abcdefeghi"))
  (is (letter-between? "aaa")))

(deftest nicenests
  (is (nice? "ugknbfddgicrmopn"))
  (is (nice? "aaa"))
  (is (not (nice? "jchzalrnumimnmhp")))
  (is (not (nice? "haegwjzuvuyypxyu")))
  (is (not (nice? "dvszwmarrgswjxmb"))))

(deftest better-nicenests
  (is (better-nice? "qjhvhtzxzqqjkmpb"))
  (is (better-nice? "xxyxx"))
  (is (not (better-nice? "uurcxstgmygtbstg")))
  (is (not (better-nice? "ieodomkazucvgmuy"))))

(deftest results
  (is (= 238 part-1))
  (is (= 69 part-2)))
