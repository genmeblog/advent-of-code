(ns advent-of-code-2015.day03-test
  (:require [advent-of-code-2015.day03 :as sut]
            [clojure.test :as t]))

(t/deftest visited-with-present-test
  (t/is (= 2 (sut/visited-with-present ">")))
  (t/is (= 4 (sut/visited-with-present "^>v<")))
  (t/is (= 2 (sut/visited-with-present "^v^v^v^v^v"))))

(t/deftest visited-with-robo-test
  (t/is (= 3 (sut/visited-with-robo "^v")))
  (t/is (= 3 (sut/visited-with-robo "^>v<")))
  (t/is (= 11 (sut/visited-with-robo "^v^v^v^v^v"))))

(t/deftest sut
  (t/is (= 2572 sut/part-1))
  (t/is (= 2631 sut/part-2)))
