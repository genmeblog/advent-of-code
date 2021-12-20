(ns advent-of-code-2021.day18)

(def tree (read-string "[[[[4,3],4],4],[7,[[8,4],9]]]"))

(def add vector)

(add tree [1 1])


(defn split
  [n]
  (let [v (int (/ n 2))]
    [v (- n v)]))


