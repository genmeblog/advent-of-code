(ns advent-of-code-2023.day05-test
  (:require [advent-of-code-2023.day05 :as sut]
            [clojure.test :as t]
            [common :refer [str-as-blocks]]))

(def data-1 (sut/parse-data (str-as-blocks "seeds: 79 14 55 13

seed-to-soil map:
50 98 2
52 50 48

soil-to-fertilizer map:
0 15 37
37 52 2
39 0 15

fertilizer-to-water map:
49 53 8
0 11 42
42 0 7
57 7 4

water-to-light map:
88 18 7
18 25 70

light-to-temperature map:
45 77 23
81 45 19
68 64 13

temperature-to-humidity map:
0 69 1
1 0 69

humidity-to-location map:
60 56 37
56 93 4")))

(t/deftest example
  (t/is (= 35 (sut/find-location data-1 sut/seeds-ids)))
  (t/is (= 46 (sut/find-location data-1 sut/seeds-ranges))))

(t/deftest sut
  (t/is (= 806029445 sut/part-1))
  (t/is (= 59370572 sut/part-2)))
