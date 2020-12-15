(ns advent-of-code-2020.day15-test
  (:require [advent-of-code-2020.day15 :as sut]
            [clojure.test :as t]))

(t/deftest part-2020
  (t/is (= 436 (sut/game [0 3 6] 2020)))
  (t/is (= 1 (sut/game [1 3 2] 2020)))
  (t/is (= 10 (sut/game [2 1 3] 2020)))
  (t/is (= 27 (sut/game [1 2 3] 2020)))
  (t/is (= 78 (sut/game [2 3 1] 2020)))
  (t/is (= 438 (sut/game [3 2 1] 2020)))
  (t/is (= 1836 (sut/game [3 1 2] 2020))))

(t/deftest part-3e7
  (t/is (= 175594 (time (sut/game [0 3 6] 30000000))))
  (t/is (= 2578 (time (sut/game [1 3 2] 30000000))))
  (t/is (= 3544142 (time (sut/game [2 1 3] 30000000))))
  (t/is (= 261214 (time (sut/game [1 2 3] 30000000))))
  (t/is (= 6895259 (time (sut/game [2 3 1] 30000000))))
  (t/is (= 18 (time (sut/game [3 2 1] 30000000))))
  (t/is (= 362 (time (sut/game [3 1 2] 30000000)))))

(t/deftest sut
  (t/is (= 1696 sut/part-1))
  (t/is (= 37385 sut/part-2)))
