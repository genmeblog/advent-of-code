(ns advent-of-code-2018.day02-test
  (:require [advent-of-code-2018.day02 :as sut]
            [clojure.test :as t]
            [clojure.string :as str]))

(def data-1 (str/split-lines "abcdef
bababc
abbcde
abcccd
aabcdd
abcdee
ababab"))

(def data-2 (str/split-lines "abcde
fghij
klmno
pqrst
fguij
axcye
wvxyz"))

(t/deftest checksum
  (t/is (= 12 (sut/checksum data-1))))

(t/deftest correct
  (t/is (= "fgij" (sut/common-letters data-2))))

(t/deftest sut
  (t/is (= 7808 sut/part-1))
  (t/is (= "efmyhuckqldtwjyvisipargno" sut/part-2)))
