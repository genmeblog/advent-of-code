(ns advent-of-code-2024.day03
  (:require [common :refer [read-single-line]]))

(def data (read-single-line 2024 03))

(defn find-all [data]
  (re-seq #"mul\((\d{1,3}),(\d{1,3})\)" data))

(defn multiply [[_ a b]] (* (parse-long (or a "0")) (parse-long (or b "0"))))

(defn multiply-and-add [data] (transduce (map multiply) + data))

(def part-1 (multiply-and-add (find-all data)))
;; => 188741603

(defn filter-step [[buff in?] [v :as all]]
  [(if in? (conj buff all) buff)
   (condp = v "do()" true "don't()" false in?)])

(defn find-surrounded [data]
  (->> data
       (re-seq #"do\(\)|don't\(\)|mul\((\d{1,3}),(\d{1,3})\)")
       (reduce filter-step [[] true])
       (first)))

(def part-2 (multiply-and-add (find-surrounded data)))
;; => 67269798
