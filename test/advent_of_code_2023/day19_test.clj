(ns advent-of-code-2023.day19-test
  (:require [advent-of-code-2023.day19 :as sut]
            [clojure.test :as t]
            [common :refer [read-data-as-blocks]]))

(def data (sut/parse (read-data-as-blocks "examples" 2023 19)))

(t/deftest example
  (t/is (= 19114 (sut/find-accepted data)))
  (t/is (= 167409079868000 (sut/find-accepted-ranges data))))

(t/deftest sut
  (t/is (= 353553 sut/part-1))
  (t/is (= 124615747767410 sut/part-2)))
