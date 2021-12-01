(ns advent-of-code-2016.day09
  (:require [common :refer [read-data]]))

(def data (apply str (read-data 2016 9)))

(defn parse-marker
  [s]
  (->> (apply str s)
       (re-find #"(\d+)x(\d+)")
       (rest)
       (map read-string)))

(defn process-seq
  [r]
  (let [[r1 r2] (split-with #(not= % \)) r)
        [chcnt rep] (parse-marker r1)
        [f newr] (split-at chcnt (rest r2))]
    [f rep newr]))

(defmacro looper
  [buffer processor1 processor2]
  `(loop [[~'l & r#] ~'data
          ~'buff ~buffer]
     (if-not ~'l
       ~'buff
       (if (= ~'l \()
         (let [[~'f ~'rep newr#] (process-seq r#)]
           (recur newr# ~processor1))
         (recur r# ~processor2)))))

(defn parse-data [data]
  (count (looper [] (reduce conj buff (mapcat identity (repeat rep f))) (conj buff l))))

(def part-1 (parse-data data))
;; => 74532

(defn parse-data-all [data]
  (looper 0 (+ buff (* rep (parse-data-all f))) (inc buff)))

(def part-2 (parse-data-all data))
;; => 11558231665
