(ns advent-of-code-2015.day08-test
  (:require [advent-of-code-2015.day08 :as sut]
            [clojure.test :as t]
            [clojure.string :as str]))

(def data (str/split-lines "\"\"
\"abc\"
\"aaa\\\"aaa\"
\"\\x27\""))

(t/deftest counts
  (t/is (= [2 5 10 6] (map count data)))
  (t/is (= [0 3 7 1] (map sut/interpret data)))
  (t/is (= [6 9 16 11] (map #(+ 2 (count (str/escape % sut/escapes))) data))))

(t/deftest diffs
  (t/is (= 12 (sut/find-counts-1 data)))
  (t/is (= 19 (sut/find-counts-2 data))))

(t/deftest sut
  (t/is (= 1333 sut/part-1))
  (t/is (= 2046 sut/part-2)))
