(ns advent-of-code-2022.day22
  (:require [common :refer [read-data-as-blocks]]))

(defn parse-movement
  [line]
  (re-seq #"\d+|[LR]" line))

(def data (read-data-as-blocks 2022 22))

(parse-movement (first (second data)))


