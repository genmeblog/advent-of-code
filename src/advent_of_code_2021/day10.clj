(ns advent-of-code-2021.day10
  (:require [common :refer [read-data]]
            [fastmath.stats :as stats]))

(def cl ")]}>")
(def closing-chars (zipmap "([{<" cl))
(def points-part-1 (zipmap cl [3 57 1197 25137]))
(def points-part-2 (zipmap cl [1 2 3 4]))

(defn score-or-stack
  [line]
  (reduce (fn [stack ch]
            (if (#{\[\{\(\<} ch)
              (conj stack ch)
              (if (= (closing-chars (first stack)) ch)
                (rest stack)
                (reduced (points-part-1 ch))))) nil line))

(defn parse
  [data]
  (->> data
       (map score-or-stack)
       (group-by number?)))

(def data (parse (read-data 2021 10)))

(def part-1 (reduce + (data true)))
;; => 343863

(defn incomplete-score
  [stack]
  (->> stack
       (map closing-chars)
       (reduce #(+ (points-part-2 %2) (* 5 %1)) 0)))

(defn incomplete-contest-score
  [data]
  (->> (data false)
       (map incomplete-score)
       (stats/median)
       (long)))

(def part-2 (incomplete-contest-score data))
;; => 2924734236
