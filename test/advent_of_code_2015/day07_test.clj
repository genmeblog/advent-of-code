(ns advent-of-code-2015.day07-test
  (:require [advent-of-code-2015.day07 :as sut]
            [clojure.test :as t]
            [clojure.string :as str]))

(def data (->> "123 -> x
456 -> y
x AND y -> d
x OR y -> e
x LSHIFT 2 -> f
y RSHIFT 2 -> g
NOT x -> h
NOT y -> i"
               (str/split-lines)
               (map sut/parse)
               (into {})))

(t/deftest simple-wires
  (t/is (= [72 507 492 114 65412 65079 123 456] (map (partial sut/wire-value data) '(d e f g h i x y)))))

(t/deftest sut
  (t/is (= 16076 sut/part-1))
  (t/is (= 2797 sut/part-2)))
