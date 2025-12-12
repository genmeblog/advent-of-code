(ns advent-of-code-2025.day12
  (:require [common :refer [read-data-as-blocks sum get-numbers]]))

(defn parse-sizes [sizes] (map get-numbers sizes))

(def data (parse-sizes (last (read-data-as-blocks 2025 12))))

(def part-1 (reduce (fn [cnt [w h & r]]
                    (if (>= (* w h) (* 9 (sum r))) (inc cnt) cnt)) 0 data))
;; => 410
