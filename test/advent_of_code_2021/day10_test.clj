(ns advent-of-code-2021.day10-test
  (:require [advent-of-code-2021.day10 :as sut]
            [clojure.test :as t]))

(def data (sut/parse ["[({(<(())[]>[[{[]{<()<>>"
                      "[(()[<>])]({[<{<<[]>>("
                      "{([(<{}[<>[]}>{[]{[(<()>"
                      "(((({<>}<{<{<>}{[]{[]{}"
                      "[[<[([]))<([[{}[[()]]]"
                      "[{[{({}]{}}([{[{{{}}([]"
                      "{<[[]]>}<{[{[{[]{()[[[]"
                      "[<(<(<(<{}))><([]([]()"
                      "<{([([[(<>()){}]>(<<{{"
                      "<{([{{}}[<[[[<>{}]]]>[]]"]))

(t/deftest corrupted-score
  (t/are [ch in] (= (sut/points-part-1 ch) (sut/score-or-stack in))
    \} "{([(<{}[<>[]}>{[]{[(<()>"
    \) "[[<[([]))<([[{}[[()]]]"
    \] "[{[{({}]{}}([{[{{{}}([]"
    \) "[<(<(<(<{}))><([]([]()"
    \> "<{([([[(<>()){}]>(<<{{"))

(t/deftest incomplete-score
  (t/are [s in] (= s (sut/incomplete-score (sut/score-or-stack in)))
    288957 "[({(<(())[]>[[{[]{<()<>>"
    5566 "[(()[<>])]({[<{<<[]>>("
    1480781 "(((({<>}<{<{<>}{[]{[]{}"
    995444 "{<[[]]>}<{[{[{[]{()[[[]"
    294 "<{([{{}}[<[[[<>{}]]]>[]]"))

(t/deftest test-data
  (t/is (= 26397 (reduce + (data true))))
  (t/is (= 288957 (sut/incomplete-contest-score data))))

(t/deftest sut
  (t/is (= 343863 sut/part-1))
  (t/is (= 2924734236 sut/part-2)))
