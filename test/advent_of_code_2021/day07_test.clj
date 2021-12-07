(ns advent-of-code-2021.day07-test
  (:require [advent-of-code-2021.day07 :as sut]
            [clojure.test :as t]
            [fastmath.stats :as stats]))

(def data [16,1,2,0,4,2,7,1,2,14])

(t/deftest test-data
  (t/is (= 37 (sut/fuel-burn data sut/dist (stats/median data))))
  (t/is (= 168 (sut/fuel-burn-valid data))))

(t/deftest sut
  (t/is (= 336040 sut/part-1))
  (t/is (= 94813675 sut/part-2)))
