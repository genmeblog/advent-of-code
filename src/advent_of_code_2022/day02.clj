(ns advent-of-code-2022.day02
  (:require [common :refer [read-data split-line]]))

(defn parse [line]
  (update (split-line line) 1 {"X" 1 "Y" 2 "Z" 3}))

(def data (map parse (read-data 2022 2)))

(def pairs
  {["A" 1] 3 ["A" 2] 6 ["A" 3] 0
   ["B" 1] 0 ["B" 2] 3 ["B" 3] 6
   ["C" 1] 6 ["C" 2] 0 ["C" 3] 3})

(defn part-1-calc [data]
  (reduce (fn [sum pair]
            (+ sum (pairs pair) (second pair))) 0 data))

(def part-1 (part-1-calc data))
;; => 12276

(def reversed {1 {"C" 2 "A" 3 "B" 1}
             2 {"C" 3 "B" 2 "A" 1}
             3 {"A" 2 "C" 1 "B" 3}})

(defn part-2-calc [data]
  (reduce (fn [sum [a b]]
            (+ sum (* 3 (dec b)) ((reversed b) a))) 0 data))

(def part-2 (part-2-calc data))
;; => 9975
