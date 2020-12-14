(ns advent-of-code-2020.day13-test
  (:require [advent-of-code-2020.day13 :as sut]
            [clojure.test :as t]
            [clojure.string :as str]))

(def data (sut/parse (str/split-lines "939
7,13,x,x,59,x,31,19")))

(def data1 (sut/parse (str/split-lines "3417
17,x,13,19")))

(def data2 (sut/parse (str/split-lines "754018
67,7,59,61")))

(def data3 (sut/parse (str/split-lines "779210
67,x,7,59,61")))

(def data4 (sut/parse (str/split-lines "1261476
67,7,x,59,61")))

(def data5 (sut/parse (str/split-lines "1202161486
1789,37,47,1889")))

(t/deftest earliest-depart
  (t/is (= 295 (sut/nearest-depart data))))

(t/deftest earliest-timestamp
  (t/is (= 1068781 (sut/process data)))
  (t/are [d] (= (:timestamp d) (sut/process d))
    data1 data2 data3 data4 data5))

(t/deftest sut
  (t/is (= 4808 sut/part-1))
  (t/is (= 741745043105674 sut/part-2)))

