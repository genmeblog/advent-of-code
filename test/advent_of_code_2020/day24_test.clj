(ns advent-of-code-2020.day24-test
  (:require [advent-of-code-2020.day24 :as sut]
            [clojure.test :as t]
            [clojure.string :as str]))

(def data (map sut/fix (str/split-lines "sesenwnenenewseeswwswswwnenewsewsw
neeenesenwnwwswnenewnwwsewnenwseswesw
seswneswswsenwwnwse
nwnwneseeswswnenewneswwnewseswneseene
swweswneswnenwsewnwneneseenw
eesenwseswswnenwswnwnwsewwnwsene
sewnenenenesenwsewnenwwwse
wenwwweseeeweswwwnwwe
wsweesenenewnwwnwsenewsenwwsesesenwne
neeswseenwwswnwswswnw
nenwswwsewswnenenewsenwsenwnesesenew
enewnwewneswsewnwswenweswnenwsenwsw
sweneswneswneneenwnewenewwneswswnese
swwesenesewenwneswnwwneseswwne
enesenwswwswneneswsenwnewswseenwsese
wnwnesenesenenwwnenwsewesewsesesew
nenewswnwewswnenesenwnesewesw
eneswnwswnwsenenwnwnwwseeswneewsenese
neswnwewnwnwseenwseesewsenwsweewe
wseweeenwnesenwwwswnew")))

(t/deftest example
  (t/is (= 10 (count (sut/flip-tiles data))))
  (t/is (= 2208 (sut/gol data 100))))

(t/deftest sut
  (t/is (= 266 sut/part-1))
  (t/is (= 3627 sut/part-2)))
