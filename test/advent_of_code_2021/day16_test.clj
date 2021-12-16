(ns advent-of-code-2021.day16-test
  (:require [advent-of-code-2021.day16 :as sut]
            [clojure.test :as t]))

(t/deftest content
  (t/are [s r] (= (sut/parse s) r)
    "D2FE28" [{:version 6, :id 4, :number 2021}]
    "38006F45291200" [{:version 1, :id 6, :subpacket [{:version 6, :id 4, :number 10}
                                                      {:version 2, :id 4, :number 20}]}]
    "EE00D40C823060" [{:version 7, :id 3, :subpacket [{:version 2, :id 4, :number 1}
                                                      {:version 4, :id 4, :number 2}
                                                      {:version 1, :id 4, :number 3}]}]))

(t/deftest part1-tests
  (t/are [s r] (= (sut/count-versions (sut/parse s)) r)
    "8A004A801A8002F478" 16
    "620080001611562C8802118E34" 12
    "C0015000016115A2E0802F182340" 23
    "A0016C880162017C3686B18A3D4780" 31))

(t/deftest part2-tests
  (t/are [s r] (= (sut/execute (first (sut/parse s))) r)
    "C200B40A82" 3
    "04005AC33890" 54
    "880086C3E88112" 7
    "CE00C43D881120" 9
    "D8005AC2A8F0" 1
    "F600BC2D8F" 0
    "9C005AC2F8F0" 0
    "9C0141080250320F1802104A08" 1))

(t/deftest sut
  (t/is (= 893 sut/part-1))
  (t/is (= 4358595186090 sut/part-2)))
