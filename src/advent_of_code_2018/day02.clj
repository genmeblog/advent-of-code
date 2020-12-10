(ns advent-of-code-2018.day02
  (:require [clojure.java.io :as io]))

(set! *unchecked-math* :warn-on-boxed)
(set! *warn-on-reflection* true)

(def ids
  (-> (io/resource "day02.txt")
      (io/reader)
      (line-seq)
      (delay)))

;; part 1

;; frequencies as list of sets
(def freqs (delay (map #(->> %
                             (frequencies)
                             (group-by second)
                             (keys)
                             (set)) @ids)))

(defn how-many
  "How many sets with given value"
  ^long [val]
  (count (filter #(% val) @freqs)))

;; part 2

(defn str-diff
  "Difference between two strings. Returns 0 when strings differ by exactly one character."
  ^long [a b]
  (dec ^long (reduce + (map (fn [a b] (if (= a b) 0 1)) a b))))

(def correct-boxes
  (delay (for [a @ids
               b @ids
               :when (zero? (str-diff a b))]
           [a b])))

(def common-letters (->> @correct-boxes
                         (map (fn [[a b]] (map #(if (= %1 %2) %1 nil) a b)))
                         (map (partial apply str))
                         (distinct)
                         (first)
                         (delay)))

(time {:checksum (* (how-many 2) (how-many 3))
       :common-letters @common-letters})
;; => {:checksum 7808, :common-letters "efmyhuckqldtwjyvisipargno"}
