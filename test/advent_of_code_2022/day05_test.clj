(ns advent-of-code-2022.day05-test
  (:require [common :refer [str-as-blocks]]
            [advent-of-code-2022.day05 :as sut]
            [clojure.test :as t]))


(def data (sut/parse (str-as-blocks "    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2" false)))

(t/deftest example
  (t/is (= "CMZ" (sut/move data false)))
  (t/is (= "MCD" (sut/move data true))))

(t/deftest sut
  (t/is (= "BZLVHBWQF" sut/part-1))
  (t/is (= "TDGJQTZSL" sut/part-2)))
