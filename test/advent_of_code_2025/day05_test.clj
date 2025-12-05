(ns advent-of-code-2025.day05-test
  (:require [advent-of-code-2025.day05 :as sut]
            [clojure.test :as t]
            [common :refer [read-data-as-blocks]]))

(def data (sut/parse (read-data-as-blocks "examples" 2025 5)))

(t/deftest example
  (t/is (= 3 (sut/fresh data)))
  (t/is (= 14 (sut/all-fresh data))))

(t/deftest sut
  (t/is (= 739 sut/part-1))
  (t/is (= 344486348901788 sut/part-2)))
