(ns advent-of-code-2022.day16-test
  (:require [advent-of-code-2022.day16 :as sut]
            [clojure.test :as t]))

(def data2 (sut/parse-data ["Valve AA has flow rate=0; tunnels lead to valves DD, II, BB"
                          "Valve BB has flow rate=13; tunnels lead to valves CC, AA"
                          "Valve CC has flow rate=2; tunnels lead to valves DD, BB"
                          "Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE"
                          "Valve EE has flow rate=3; tunnels lead to valves FF, DD"
                          "Valve FF has flow rate=0; tunnels lead to valves EE, GG"
                          "Valve GG has flow rate=0; tunnels lead to valves FF, HH"
                          "Valve HH has flow rate=22; tunnel leads to valve GG"
                          "Valve II has flow rate=0; tunnels lead to valves AA, JJ"
                          "Valve JJ has flow rate=21; tunnel leads to valve II"]))

(t/deftest example
  (t/is (= 1651 (sut/traverse data2)))
  (t/is (= 1707 (sut/traverse2 data2))))

(t/deftest sut
  (t/is (= 2080 sut/part-1))
  #_(t/is (= 2752 sut/part-2)))
