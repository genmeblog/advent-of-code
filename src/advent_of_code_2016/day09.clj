(ns advent-of-code-2016.day09
  (:require [common :refer [read-data]]))

(def data (apply str (read-data 2016 9)))

(defn parse-marker
  [s]
  (->> (apply str s)
       (re-find #"(\d+)x(\d+)")
       (rest)
       (map read-string)))

(def part-1 (count (loop [[l & r] data
                          buff []]
                     (if-not l
                       buff
                       (if (= l \()
                         (let [[r1 r2] (split-with #(not= % \)) r)
                               [chcnt rep] (parse-marker r1)
                               [f newr] (split-at chcnt (rest r2))]
                           (recur newr (reduce conj buff (mapcat identity (repeat rep f)))))
                         (recur r (conj buff l)))))))
;; => 74532

