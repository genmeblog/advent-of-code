(ns advent-of-code-2024.day17-test
  (:require [advent-of-code-2024.day17 :as sut]
            [clojure.test :as t]
            [common :refer [read-data-as-blocks]]))

(def data (sut/parse (read-data-as-blocks "examples" 2024 17)))

(defn A [data] ((sut/run data) 0))
(defn B [data] ((sut/run data) 1))

(t/deftest examples
  (t/is (= 1 (B [[0 0 9 0 []] [2 6]])))
  (t/is (= [0 1 2] (sut/output [[10 0 0 0 []] [5 0 5 1 5 2]])))
  (t/is (= 0 (A [[2024 0 0 0 []] [0 1 5 4 3 0]])))
  (t/is (= [4,2,5,6,7,7,7,7,3,1,0] (sut/output [[2024 0 0 0 []] [0 1 5 4 3 0]])))
  (t/is (= 26 (B [[0 29 0 0 []] [1 7]])))
  (t/is (= 44354 (B [[0 2024 43690 0 []] [4 0]])))
  (t/is (= "4,6,3,5,6,3,5,2,1,0" (sut/output->str data))))

(t/deftest sut
  (t/is (= "7,3,0,5,7,1,4,0,5" sut/part-1))
  (t/is (= 202972175280682 sut/part-2)))
