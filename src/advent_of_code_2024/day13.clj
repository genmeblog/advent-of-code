(ns advent-of-code-2024.day13
  (:require [common :refer [read-data-as-blocks get-numbers sum]]
            [fastmath.optimization :as opt]))

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


;; Linear programming approach:
;;
;; Button A: X+94, Y+34
;; Button B: X+22, Y+67
;; Prize: X=8400, Y=5400
(fastmath.optimization/linear-optimization [3 1 0] [[94 22] :eq 8400
                                                    [34 67] :eq 5400
                                                    [1 0] :leq 100
                                                    [0 1] :leq 100])
;; => [(80.0 40.00000000000001) 280.0]
