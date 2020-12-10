(ns advent-of-code-2015.day05
  (:require [clojure.java.io :as io]))

(set! *unchecked-math* :warn-on-boxed)
(set! *warn-on-reflection* true)

(def strings (-> (io/resource "day05.txt")
                 (io/reader)
                 (line-seq)))

(defn three-vowels? [s] (>= (count (filter #{\a \e \i \o \u} s)) 3))
(defn double-letter? [ps] (some (fn [[a b]] (= a b)) ps))
(defn bad-pairs? [ps] (some #{'(\a \b) '(\c \d) '(\p \q) '(\x \y)} ps))

(defn nice?
  [s]
  (let [ps (partition 2 1 s)]
    (and (three-vowels? s)
         (double-letter? ps)
         (not (bad-pairs? ps)))))

(def part-1 (count (filter nice? strings)))

(defn letter-between? [s] (some (fn [[a _ b]] (= a b)) (partition 3 1 s)))
(defn double-doubles?
  [s]
  (->> (partition 2 1 s)
       (reduce (fn [[[a b] lst] [na nb :as new]]
                 (if (and (= a na) (= b nb))
                   [nil lst]
                   [new (conj lst new)])) [])
       (second)
       (frequencies)
       (vals)
       (some #(> ^long % 1))))

(defn better-nice? [s] (and (double-doubles? s) (letter-between? s)))

(def part-2 (count (filter better-nice? strings)))
