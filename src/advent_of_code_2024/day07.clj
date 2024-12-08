(ns advent-of-code-2024.day07
  (:require [common :refer [read-data get-numbers]]))

(def data (map get-numbers (read-data 2024 07)))

(defn calculate
  ([ops [t v & r]] (calculate ops t [v] r))
  ([ops ^long t curr [v & rst]]
   (if-not v
     (if (some (fn [^long c] (== t c)) curr) t 0)
     (recur ops t (->> (mapcat (fn [^long c] (map #(% c v) ops)) curr)
                       (remove (fn [^long c] (> c t)))) rst))))

(defn answer [ops data]
  (reduce + (pmap (partial calculate ops) data)))

(def part-1 (answer [* +] data))
;; => 850435817339

(defn || [a b] (parse-long (str a b)))  

(def part-2 (answer [* + ||] data))
;; => 104824810233437
