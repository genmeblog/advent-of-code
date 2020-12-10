(ns advent-of-code-2015.day03-test
  (:require [advent-of-code-2015.day03 :refer :all]
            [clojure.test :refer :all]))

(deftest visited-with-present-test
  (is (= 2 (visited-with-present ">")))
  (is (= 4 (visited-with-present "^>v<")))
  (is (= 2 (visited-with-present "^v^v^v^v^v"))))

(deftest visited-with-robo-test
  (is (= 3 (visited-with-robo "^v")))
  (is (= 3 (visited-with-robo "^>v<")))
  (is (= 11 (visited-with-robo "^v^v^v^v^v"))))

(deftest results
  (is (= 2572 part-1))
  (is (= 2631 part-2)))
