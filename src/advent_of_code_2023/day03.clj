(ns advent-of-code-2023.day03
  (:require [common :refer [read-data]]
            [clojure.string :as str]))

(defn find-all-positions [re l]
  (->> (re-seq re l)
       (reduce (fn [[currpos buff] substr]
                 (let [chpos (str/index-of l substr currpos)]
                   [(+ chpos (count substr))
                    (conj buff [chpos substr])])) [0 []])
       second
       (into {})))

(defn process-lines [data re]
  (->> (for [[row l] (map-indexed vector data)]
         [row (find-all-positions re l)])
       (into {})))

(defn parse-data [data]
  {:numbers (process-lines data #"\d+")
   :symbols (process-lines data #"[^\d\.]+")})

(def data (parse-data (read-data 2023 3)))

(defn overlap? [from to [x s]]
  (and (<= x to)
       (<= from (+ x (count s) -1))))

(defn near-by-row [from to cols]
  (filter (partial overlap? from to) cols))

(defn near [lookup row [col obj]]
  [obj (->> [(dec row) row (inc row)]
            (map lookup)
            (mapcat (partial near-by-row (dec col) (+ col (count obj))))
            (seq))])

(defn adjacent [lookup [row cols]]
  (map (partial near lookup row) cols))

(defn adjacent-all [data source target]
  (mapcat (partial adjacent (data target)) (data source)))

(defn produce-result [data source target filter-data extract-number]
  (->> (adjacent-all data source target)
       (filter filter-data)
       (map extract-number)
       (reduce +)))

(defn part-numbers [data]
  (produce-result data :numbers :symbols second (comp parse-long first)))

(def part-1 (part-numbers data))
;; => 520135

(defn gear? [[s ns]]
  (and (= "*" s) (= 2 (count ns))))

(defn gear-ratio [[[_ a] [_ b]]]
  (* (parse-long a)
     (parse-long b)))

(defn gears-ratios [data]
  (produce-result data :symbols :numbers gear? (comp gear-ratio second)))

(def part-2 (gears-ratios data))
;; => 72514855

