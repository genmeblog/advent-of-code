(ns advent-of-code-2015.day07
  (:require [fastmath.core :as m]
            [clojure.string :as s]))

(defmacro w
  [a]
  `(let [a# (read-string ~a)]
     (if (number? a#)
       (constantly a#)
       () (keyword a#))))

(defn ASSIGN [a] (w a))

(ASSIGN "1")

(defn bobby-tables
  [input]
  (reduce (fn [bt line]
            (let [s (s/split line #" ")]
              (assoc bt
                     (keyword (last s))
                     (case (count s)
                       3 (ASSIGN (first s)))))) {} input))


(keyword (read-string "a"))
