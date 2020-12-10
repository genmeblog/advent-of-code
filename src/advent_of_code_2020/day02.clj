(ns advent-of-code-2020.day02
  (:require [common :refer [read-data]]))

(def regex #"(\d+)-(\d+) (\w+): (\w+)")

(defn parse
  [in]
  (let [[mn mx letter password] (rest (re-matches regex in))]
    [(Integer. mn) (Integer. mx) (first letter) password]))

(def data (map parse (read-data 2020 2)))

(defn how-many-valid
  [validator strs]
  (count (filter identity (map validator strs))))

(defn valid1?
  [[mn mx letter password]]
  (<= mn (get (frequencies password) letter 0) mx))

(def part-1 (time (how-many-valid valid1? data)))
;; => 396

;;

(defn xor [a b] (or (and a (not b))
                    (and b (not a))))

(defn valid2?
  [[pos1 pos2 letter password]]
  (let [l1 (get password (dec pos1))
        l2 (get password (dec pos2))]
    (xor (= l1 letter) (= l2 letter))))

(def part-2 (time (how-many-valid valid2? data)))
;; => 428
