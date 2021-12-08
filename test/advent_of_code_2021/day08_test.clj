(ns advent-of-code-2021.day08-test
  (:require [advent-of-code-2021.day08 :as sut]
            [clojure.test :as t]))

(def data (->> ["be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe"
                "edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc"
                "fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg"
                "fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb"
                "aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea"
                "fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb"
                "dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe"
                "bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef"
                "egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb"
                "gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce"]
               (map sut/split-and-pack)))

(def data-line (map sut/split-and-pack ["acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf"]))

(t/deftest part-1-tests
  (t/is (= 26 (sut/easy-numbers data)))
  (t/is (zero? (sut/easy-numbers data-line))))

(t/deftest part-2-tests
  (t/is (= 5353 (sut/read-digits (first data-line))))
  (t/is (= 61229 (reduce + (map sut/read-digits data))))
  (t/is (= [8394 9781 1197 9361 4873 8418 4548 1625 8717 4315] (map sut/read-digits data))))

(t/deftest sut
  (t/is (= 493 sut/part-1))
  (t/is (= 1010460 sut/part-2)))
