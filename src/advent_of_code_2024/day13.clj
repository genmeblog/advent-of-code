(ns advent-of-code-2024.day13
  (:require [common :refer [read-data-as-blocks get-numbers sum]]))

(defn process-block [block] (mapcat get-numbers block))

(def data (map process-block (read-data-as-blocks 2024 13)))

(defn solve [part2? [a c b d x y]]
  (let [det (- (* a d) (* b c))
        x (if part2? (+ x 10000000000000) x)
        y (if part2? (+ y 10000000000000) y)
        p1 (/ (- (* d x) (* b y)) det)
        p2 (/ (- (* a y) (* c x)) det)]
    (if (and (integer? p1) (integer? p2)
             (or part2? (and (<= p1 100) (<= p2 100))))
      (+ (* 3 p1) p2) 0)))

(defn prizes [data part2?] (sum (map (partial solve part2?) data)))

(def part-1 (prizes data false))
;; => 28059

(def part-2 (prizes data true))
;; => 102255878088512
