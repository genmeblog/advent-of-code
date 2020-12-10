(ns advent-of-code-2020.day05
  (:require [common :refer [read-data]]
            [clojure.string :as str]))

(defn ->number
  [s]
  (-> (str/escape s {\F 0 \L 0 \B 1 \R 1})
      (Integer/parseInt 2)))

(def data (sort (map ->number (read-data 2020 5))))

(def part-1 (last data))
;; 855

(def part-2 (reduce (fn [prv nxt]
                      (if (= (- nxt prv) 2)
                        (reduced (inc prv))
                        nxt)) data))
;; 552
