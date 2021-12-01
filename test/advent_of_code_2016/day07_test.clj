(ns advent-of-code-2016.day07-test
  (:require [advent-of-code-2016.day07 :as sut]
            [clojure.test :as t]))

(def data (mapv sut/parser ["abba[mnop]qrst" "abcd[bddb]xyyx" "aaaa[qwer]tyui" "ioxxoj[asdfgh]zxcvbn"
                            "aba[bab]xyz" "xyx[xyx]xyx" "aaa[kek]eke" "zazbz[bzb]cdb"]))

(t/deftest part-1-tests
  (t/is (sut/supports-tls? (data 0)))
  (t/is (not (sut/supports-tls? (data 1))))
  (t/is (not (sut/supports-tls? (data 2))))
  (t/is (sut/supports-tls? (data 3))))

(t/deftest part-2-tests
  (t/is (sut/supports-ssl? (data 4)))
  (t/is (not (sut/supports-ssl? (data 5))))
  (t/is (sut/supports-ssl? (data 6)))
  (t/is (sut/supports-ssl? (data 7))))

(t/deftest sut
  (t/is (= 105 sut/part-1))
  (t/is (= 258 sut/part-2)))
