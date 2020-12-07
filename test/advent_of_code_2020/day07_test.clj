(ns advent-of-code-2020.day07-test
  (:require [advent-of-code-2020.day07 :as sut]
            [clojure.test :as t]
            [clojure.string :as str]))

(def data-1 (->> (str/split-lines "light red bags contain 1 bright white bag, 2 muted yellow bags.
dark orange bags contain 3 bright white bags, 4 muted yellow bags.
bright white bags contain 1 shiny gold bag.
muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
dark olive bags contain 3 faded blue bags, 4 dotted black bags.
vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
faded blue bags contain no other bags.
dotted black bags contain no other bags.")
                 (mapcat sut/parse)))

(def data-2 (->> (str/split-lines "shiny gold bags contain 2 dark red bags.
dark red bags contain 2 dark orange bags.
dark orange bags contain 2 dark yellow bags.
dark yellow bags contain 2 dark green bags.
dark green bags contain 2 dark blue bags.
dark blue bags contain 2 dark violet bags.
dark violet bags contain no other bags.")
                 (mapcat sut/parse)))


(t/deftest part-1-test
  (t/is (= 4 (->> "shiny gold" (sut/traverse (group-by second data-1)) second count))))

(t/deftest part-2-test
  (t/is (= 32 (sut/traverse2 (group-by first data-1) "shiny gold")))
  (t/is (= 126 (sut/traverse2 (group-by first data-2) "shiny gold"))))

;;

(t/deftest sut
  (t/is (= 296 sut/part-1))
  (t/is (= 9339 sut/part-2)))
