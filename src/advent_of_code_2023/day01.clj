(ns advent-of-code-2023.day01
  (:require [common :refer [read-data]]))

(def data (read-data 2023 1))

(def substitutes {"one" "1" "two" "2" "three" "3" "four" "4" "five" "5"
                "six" "6" "seven" "7" "eight" "8" "nine" "9"})

(defn get-digit [n] (get substitutes n n))

(def selector (juxt first last))

(defn parse-line  [re process l]
  (->> (re-seq re l)
       (selector)
       (map (comp get-digit process))
       (apply str)
       (parse-long)))

(defn summarize
  ([data re] (summarize data re identity))
  ([data re process]
   (-> (map (partial parse-line re process))
       (transduce + data))))

(defn part-1-solution [data]
  (summarize data #"\d"))

(def part-1 (part-1-solution data))
;; => 55029

(defn part-2-solution [data]
  (summarize data #"(?=(one|two|three|four|five|six|seven|eight|nine|\d))" second))

(def part-2 (part-2-solution data))
;; => 55686
