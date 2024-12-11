(ns advent-of-code-2024.day11
  (:require [common :refer [read-single-line get-numbers]]))

(def data (get-numbers (read-single-line 2024 11)))

(defn transform [^long n]
  (if (zero? n) [1]
      (let [s (str n) c (count s)]
        (if (even? c)
          (let [hc (quot c 2)]
            [(parse-long (subs s 0 hc))
             (parse-long (subs s hc))])
          [(* n 2024)]))))

(defn stones
  ([data ^long max-level] (stones data max-level 0 {}))
  ([data ^long max-level ^long level buff]
   (if (== max-level level)
     [buff (count data)]
     (reduce (fn [[b ^long sum] stone]
               (let [id [stone level]]
                 (if-let [s (buff id)]
                   [b (+ sum s)]
                   (let [[nb s] (stones (transform stone) max-level (inc level) b)]
                     [(assoc nb id s) (+ sum s)])))) [buff 0] data))))

(def part-1 (second (stones data 25)))
;; => 207683

(def part-2 (second (stones data 75)))
;; => 244782991106220

