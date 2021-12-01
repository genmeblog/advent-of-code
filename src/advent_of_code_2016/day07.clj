(ns advent-of-code-2016.day07
  (:require [common :refer [read-data]]
            [clojure.string :as str]
            [clojure.set :as set]))

(defn parser [in]
  (let [s (str/split in #"[\[\]]")]
    [(take-nth 2 s) (take-nth 2 (rest s))]))

(def data (map parser (read-data 2016 7)))

(defn abba? [[a b c d]] (and (= a d) (= b c) (not= a b)))
(defn some-abba? [in] (some abba? (partition 4 1 in)))

(defn supports-tls?
  [[a b]]
  (and (some some-abba? a)
       (not (some some-abba? b))))

(def part-1 (count (filter supports-tls? data)))
;; => 105

(defn aba? [[a b c]] (and (= a c) (not= a b)))
(defn find-aba [in]  (filter aba? (partition 3 1 in)))
(defn ->bab [[a b]] [b a b])

(defn supports-ssl?
  [[a b]]
  (let [aba (set (map ->bab (mapcat find-aba a)))]
    (seq (set/intersection aba (set (mapcat find-aba b))))))

(def part-2 (count (filter supports-ssl? data)))
;; => 258
