(ns advent-of-code-2016.day08-test
  (:require [advent-of-code-2016.day08 :as sut]
            [clojure.test :as t]))

(t/deftest part-1-test
  (t/is (= 6 (-> (sut/screen 7 3)
                 (sut/rect 3 2)
                 (sut/rotate-col 1 1)
                 (sut/rotate-row 0 4)
                 (sut/rotate-col 1 1)
                 :screen
                 count))))

(t/deftest sut
  (t/is (= 116 sut/part-1)))
