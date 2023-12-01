(ns advent-of-code-2022.day18
  (:require [common :refer [read-data split-line]]
            [clojure.string :as str]
            [clojure.set :as set]))

(defn parse
  [line]
  (mapv parse-long (split-line line #",")))

(def data (set (map parse (read-data 2022 18))))

(defn surrounding
  [[x y z]]
  (set [[(inc x) y z] [(dec x) y z]
        [x (inc y) z] [x (dec y) z]
        [x y (inc z)] [x y (dec z)]]))

(defn not-touching
  [data]
  (reduce (fn [cnt block]
            (->> (surrounding block)
                 (set/intersection data)
                 (count)
                 (- 6)
                 (+ cnt))) 0 data))

(def part-1 (not-touching data))
;; => 4300

(def data1 (set [[1 1 1] [2 1 1]]))
(def data2 (set [[2,2,2]
               [1,2,2]
               [3,2,2]
               [2,1,2]
               [2,3,2]
               [2,2,1]
               [2,2,3]
               [2,2,4]
               [2,2,6]
               [1,2,5]
               [3,2,5]
               [2,1,5]
               [2,3,5]]))

