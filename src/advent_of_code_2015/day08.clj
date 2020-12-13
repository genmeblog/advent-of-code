(ns advent-of-code-2015.day08
  (:require [common :refer [read-data]]
            [clojure.string :as str]))

(def data (read-data 2015 8))

(defn interpret
  [line]
  (loop [[letter & rst] (seq line)
         cnt (int -2)]
    (if letter
      (cond
        (and (= letter \\)
             (= (first rst) \x)) (recur (drop 3 rst) (inc cnt))
        (= letter \\) (recur (rest rst) (inc cnt))
        :else (recur rst (inc cnt)))
      cnt)))

(defn find-counts-1
  [data]
  (- (reduce + (map count data))
     (reduce + (map interpret data))))

(def part-1 (find-counts-1 data))
;; => 1333

(def escapes {\" "\\\""
              \\ "\\\\"})

(defn find-counts-2
  [data]
  (- (reduce + (map #(+ 2 (count (str/escape % escapes))) data))
     (reduce + (map count data))))

(def part-2 (find-counts-2 data))
;; => 2046
