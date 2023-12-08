(ns advent-of-code-2023.day08-test
  (:require [advent-of-code-2023.day08 :as sut]
            [clojure.test :as t]
            [common :refer [read-data]]))

(t/deftest examples
  (t/is (= 2 (sut/travel-steps-AAA-ZZZ (sut/parse-data (read-data "examples" 2023 8 1)))))
  (t/is (= 6 (sut/travel-steps-AAA-ZZZ (sut/parse-data (read-data "examples" 2023 8 2)))))
  (t/is (= 6 (sut/ghost-steps (sut/parse-data (read-data "examples" 2023 8 3))))))

(t/deftest sut
  (t/is (= 22199 sut/part-1))
  (t/is (= 13334102464297 sut/part-2)))
