(ns advent-of-code-2018.day08
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(def tree (mapv read-string (str/split (slurp (io/resource "day08.txt")) #"\s+")))

(defn process-tree
  [sum children metadata tree]
  (if (zero? children)
    (let [rst (drop metadata tree)
          sum (reduce + sum (take metadata tree))]
      [sum rst])
    (let [nchildren (first tree)
          nmetadata (second tree)
          [nsum ntree] (process-tree sum nchildren nmetadata (drop 2 tree))]
      (recur nsum (dec children) metadata ntree))))


(time {:metadata-sum (first (process-tree 0 (first tree) (second tree) (drop 2 tree)))})


(defn parse-tree
  [sum children metadata tree]
  (if (zero? children)
    (let [rst (drop metadata tree)
          sum (reduce + sum (take metadata tree))]
      [ rst])
    (let [nchildren (first tree)
          nmetadata (second tree)
          [nsum ntree] (process-tree sum nchildren nmetadata (drop 2 tree))]
      (recur nsum (dec children) metadata ntree))))



(parse-tree {} [0 3 3 1 444])
