(ns advent-of-code-2015.day09-test
  (:require [advent-of-code-2015.day09 :as sut]
            [clojure.test :as t]
            [clojure.string :as str]))

(def data-1 (->> "London to Dublin = 464
London to Belfast = 518
Dublin to Belfast = 141"
                 str/split-lines
                 (mapcat sut/parse)
                 (into {})))

(t/deftest find-path
  (t/is (= 605 (sut/find-shortest data-1)))
  (t/is (= 982 (sut/find-longest data-1))))

(t/deftest sut
  (t/is (= 141 sut/part-1))
  (t/is (= 736 sut/part-2)))
