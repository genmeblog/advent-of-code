(ns advent-of-code-2025.day06
  (:require [common :refer [read-data sum]]))

(def data (read-data 2025 6))

(defn apply-cmd
  [cmd col]
  (apply ({"*" * "+" +} cmd) (map parse-long col)))

(defn math-1
  [data]
  (let [parsed (map (partial re-seq #"\d+|\+|\*") data)
        cols (apply map vector (butlast parsed))]
    (-> (map apply-cmd (last parsed) cols) sum)))

(def part-1 (math-1 data))
;; => 6957525317641

(defn ->number
  [col]
  (->> (butlast col)
       (remove #{\space})
       (apply str)
       (parse-long)))

(defn math-2
  [data]
  (->> (apply map vector data)
       (reduce (fn [[total curr op :as state] col]
                 (if-let [number (->number col)]
                   (if (#{\* \+} (last col))
                     [(+ total curr) number ({\* * \+ +} (last col))]
                     [total (op curr number) op])
                   state)) [0 0 nil])
       (butlast)
       (sum)))

(def part-2 (math-2 data))
;; => 13215665360076
