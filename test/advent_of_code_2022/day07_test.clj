(ns advent-of-code-2022.day07-test
  (:require [advent-of-code-2022.day07 :as sut]
            [clojure.test :as t]
            [clojure.string :as str]))

(def data (-> "$ cd /
$ ls
dir a
14848514 b.txt
8504156 c.dat
dir d
$ cd a
$ ls
dir e
29116 f
2557 g
62596 h.lst
$ cd e
$ ls
584 i
$ cd ..
$ cd ..
$ cd d
$ ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k" str/split-lines sut/build-tree sut/du sut/sizes))

(t/deftest example
  (t/is (= 95437 (sut/total-small-sizes data)))
  (t/is (= 24933642 (sut/find-smallest-dir data))))

(t/deftest sut
  (t/is (= 1644735 sut/part-1))
  (t/is (= 1300850 sut/part-2)))
