(ns advent-of-code-2022.day04
  (:require [common :refer [read-data split-line]]))

(defn parse [line]
  (common/parse (split-line line #"[\-\,]")))

(def data (map parse (read-data 2022 4)))

(defn include? [[a b c d]]
  (or (<= a c d b) (<= c a b d)))

(defn overlap? [[a b c d]]
  (or (<= c b d) (<= a d b)))

(def part-1 (count (filter include? data)))
;; => 562

(def part-2 (count (filter overlap? data)))
;; => 924
