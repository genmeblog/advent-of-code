(ns advent-of-code-2016.day04-test
  (:require [advent-of-code-2016.day04 :as sut]
            [clojure.test :as t]))

(def data (mapv sut/parser ["aaaaa-bbb-z-y-x-123[abxyz]"
                            "a-b-c-d-e-f-g-h-987[abcde]"
                            "not-a-real-room-404[oarel]"
                            "totally-real-room-200[decoy]"]))

(t/deftest part-1-tests
  (t/is (sut/valid-room? (data 0)))
  (t/is (sut/valid-room? (data 1)))
  (t/is (sut/valid-room? (data 2)))
  (t/is (not (sut/valid-room? (data 3))))
  (t/is (= 1514 (reduce + (map second (filter sut/valid-room? data))))))

(t/deftest part-2-tests
  (t/is (= (seq "very encrypted name") (map (sut/decrypter 343) "qzmt-zixmtkozy-ivhz"))))

(t/deftest sut
  (t/is (= 158835 sut/part-1))
  (t/is (= 993 sut/part-2)))
