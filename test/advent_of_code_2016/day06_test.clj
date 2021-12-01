(ns advent-of-code-2016.day06-test
  (:require [advent-of-code-2016.day06 :as sut]
            [clojure.test :as t]))

(def data ["eedadn"
           "drvtee"
           "eandsr"
           "raavrd"
           "atevrs"
           "tsrnev"
           "sdttsa"
           "rasrtv"
           "nssdts"
           "ntnada"
           "svetve"
           "tesnvt"
           "vntsnd"
           "vrdear"
           "dvrsen"
           "enarar"])

(t/deftest part-1-tests
  (t/is (= "easter" (sut/decode data last))))

(t/deftest part-2-tests
  (t/is (= "advent" (sut/decode data first))))

(t/deftest sut
  (t/is (= "xhnqpqql" sut/part-1))
  (t/is (= "brhailro" sut/part-2)))
