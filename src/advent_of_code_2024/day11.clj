(ns advent-of-code-2024.day11
  (:require [common :refer [read-single-line get-numbers]]))

(def data (get-numbers (read-single-line 2024 11)))

(def transform
  (memoize (fn ^long [^long level ^long n ]
             (cond
               (zero? level) 1
               (zero? n) (transform (dec level) 1)
               :else (let [s (str n) c (count s)]
                       (if (even? c)
                         (let [hc (quot c 2)]
                           (+ (transform (dec level) (parse-long (subs s 0 hc)))
                              (transform (dec level) (parse-long (subs s hc)))))
                         (transform (dec level) (* n 2024))))))))


(defn stones [data blinks]
  (transduce (map (partial transform blinks)) + data))

(def part-1 (stones data 25))
;; => 207683

(def part-2 (stones data 75))
;; => 244782991106220

