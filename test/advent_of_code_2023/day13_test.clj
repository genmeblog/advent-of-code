(ns advent-of-code-2023.day13-test
  (:require [advent-of-code-2023.day13 :as sut]
            [clojure.test :as t]
            [common :refer [read-data-as-blocks]]))

(def data (read-data-as-blocks "examples" 2023 13))

(t/deftest example
  (t/is (= 405 (sut/solution data sut/mirrored?)))
  (t/is (= 400 (sut/solution data sut/mirrored-but-one?))))

(t/deftest sut
  (t/is (= 31877 sut/part-1))
  (t/is (= 42996 sut/part-2)))
