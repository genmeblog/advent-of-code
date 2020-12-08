(ns advent-of-code-2020.day08-test
  (:require [advent-of-code-2020.day08 :as sut]
            [clojure.test :as t]
            [clojure.string :as str]))

(def data (->> (str/split-lines "nop +0
acc +1
jmp +4
acc +3
jmp -3
acc -99
acc +1
jmp -4
acc +6")
               (mapv sut/parse)))

(t/deftest loop-test
  (t/is (= {:loop 5} (sut/execute data))))

(t/deftest simulation-test
  (t/is (= {:terminated 8} (sut/simulate data))))

(t/deftest sut
  (t/is (= {:loop 1548} sut/part-1))
  (t/is (= {:terminated 1375} sut/part-2)))
