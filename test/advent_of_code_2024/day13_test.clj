(ns advent-of-code-2024.day13-test
  (:require [advent-of-code-2024.day13 :as sut]
            [clojure.test :as t]
            [common :refer [read-data-as-blocks]]))

(def data (map sut/process-block (read-data-as-blocks "examples" 2024 13)))

(t/deftest example
  (t/is (= 480 (sut/prizes data false))))

(t/deftest sut
  (t/is (= 28059 sut/part-1))
  (t/is (= 102255878088512 sut/part-2)))
