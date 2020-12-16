(ns advent-of-code-2018.day05
  (:require [common :refer [read-single-line]]))

(def data (map int (read-single-line 2018 5)))

(defn react
  [poly]
  (reduce #(if (and (seq %1) (== 32 (Math/abs ^int (- (first %1) %2))))
             (rest %1)
             (cons %2 %1)) nil poly))

(defn removed-unit-reactions
  [data]
  (pmap (fn [^long upper ^long lower]
          (let [npolymer (remove #(or (== ^int % lower)
                                      (== ^int % upper)) data)]
            (count (react npolymer)))) (range 97 123) (range 65 91)))

(def part-1 (count (react data)))
;; => 11540

(def part-2 (reduce min (removed-unit-reactions data)))
;; => 6918
