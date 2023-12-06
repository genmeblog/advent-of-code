(ns advent-of-code-2023.day05-test
  (:require [advent-of-code-2023.day05 :as sut]
            [clojure.test :as t]
            [common :refer [read-data-as-blocks]]))

(def data (sut/parse-data (read-data-as-blocks "examples" 2023 5)))

(t/deftest example
  (t/is (= 35 (sut/find-location data sut/seeds-ids)))
  (t/is (= 46 (sut/find-location data sut/seeds-ranges))))

(t/deftest sut
  (t/is (= 806029445 sut/part-1))
  (t/is (= 59370572 sut/part-2)))
