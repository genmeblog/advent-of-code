(ns advent-of-code-2016.day03-test
  (:require [advent-of-code-2016.day03 :as sut]
            [clojure.test :as t]))

(t/deftest part-1-tests
  (t/is (not (sut/triangle? [5 10 25]))))

(t/deftest part-2-tests
  (t/is (= 6 (sut/reorder-and-count [[101 301 501]
                                     [102 302 502]
                                     [103 303 503]
                                     [201 401 601]
                                     [202 402 602]
                                     [203 403 603]]))))

(t/deftest sut
  (t/is (= 917 sut/part-1))
  (t/is (= 1649 sut/part-2)))
