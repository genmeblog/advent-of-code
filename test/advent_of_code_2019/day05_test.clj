(ns advent-of-code-2019.day05-test
  (:require [advent-of-code-2019.day05 :as sut]
            [clojure.test :as t]))

(def ex [3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31,
       1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,
       999,1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99])

(t/deftest commands
  (t/is (= 444 (sut/run-part [3 0 4 0 99] [444])))
  (t/is (= [1002 4 3 4 99] (:memory (sut/run-machine (sut/make-machine [1002 4 3 4 33] [0])))))
  (t/is (= [1101 100 -1 4 99] (:memory (sut/run-machine (sut/make-machine [1101 100 -1 4 0] [0])))))
  (t/is (= 1 (sut/run-part [3,9,8,9,10,9,4,9,99,-1,8] [8])))
  (t/is (= 0 (sut/run-part [3,9,8,9,10,9,4,9,99,-1,8] [7])))
  (t/is (= 0 (sut/run-part [3,9,7,9,10,9,4,9,99,-1,8] [8])))
  (t/is (= 1 (sut/run-part [3,9,7,9,10,9,4,9,99,-1,8] [7])))
  (t/is (= 1 (sut/run-part [3,3,1108,-1,8,3,4,3,99] [8])))
  (t/is (= 0 (sut/run-part [3,3,1108,-1,8,3,4,3,99] [7])))
  (t/is (= 0 (sut/run-part [3,3,1107,-1,8,3,4,3,99] [8])))
  (t/is (= 1 (sut/run-part [3,3,1107,-1,8,3,4,3,99] [7])))
  (t/is (= 0 (sut/run-part [3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9] [0])))
  (t/is (= 1 (sut/run-part [3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9] [1])))
  (t/is (= 1 (sut/run-part [3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9] [-1])))
  (t/is (= 999 (sut/run-part ex [-234])))
  (t/is (= 999 (sut/run-part ex [7])))
  (t/is (= 1000 (sut/run-part ex [8])))
  (t/is (= 1001 (sut/run-part ex [9])))
  (t/is (= 1001 (sut/run-part ex [1234]))))

(t/deftest sut
  (t/is (= 9219874 sut/part-1))
  (t/is (= 5893654 sut/part-2)))
