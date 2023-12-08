(ns advent-of-code-2022.day01-test
  (:require [advent-of-code-2022.day01 :as sut]
            [clojure.test :as t]
            [common :refer [read-data-as-blocks]]))

(def data (sut/parse (read-data-as-blocks "examples" 2022 1)))

(t/deftest dummy-data
  (t/is (= 24000 (reduce max data)))
  (t/is (= 45000 (->> data (sort >) (take 3) (reduce +)))))

(t/deftest sut
  (t/is (= 70374 sut/part-1))
  (t/is (= 204610 sut/part-2)))
