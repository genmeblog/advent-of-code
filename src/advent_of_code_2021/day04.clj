(ns advent-of-code-2021.day04
  (:require [common :refer [read-data-as-blocks parse]]
            [clojure.string :as str]))

(defn parser [re in] (parse (str/split in re)))

(defn make-sets
  [boards]
  (->> boards
       (apply map vector) ;; cols
       (concat boards) ;; rows
       (map set))) 

(defn process-data
  [data]
  {:input (parser #"," (ffirst data))
   :boards (->> (rest data) ;; keep rows/cols as sets
                (map #(map (partial parser #"\s+") %))
                (map make-sets))})

(def data (process-data (read-data-as-blocks 2021 4)))

(defn mark-number [n board] (map #(disj % n) board))

(defn find-winning-boards
  [{:keys [boards input]}]
  (->> (reduce (fn [[winners boards] n]
                 (let [processed (->> boards
                                      (map (partial mark-number n))
                                      (group-by (partial some empty?)))]
                   [(conj winners [n (first (processed true))])
                    (processed nil)])) [[] boards] input)
       (first)
       (filter second)))

(defn score
  [[n board]]
  (->> board
       (mapcat identity)
       (distinct)
       (reduce +)
       (* n)))

(def part-1 (score (first (find-winning-boards data))))
;; => 58838

(def part-2 (score (last (find-winning-boards data))))
;; => 6256
