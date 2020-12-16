(ns advent-of-code-2020.day16
  (:require [common :refer [read-data-as-blocks]]
            [clojure.string :as str]
            [clojure.set :refer [difference]]))

(def data (read-data-as-blocks 2020 16))

(defn parse-ranges
  [lines]
  (into {} (map (fn [line]
                  (let [l (rest (re-find #"([\w\s]+): (\d+)-(\d+) or (\d+)-(\d+)" line))
                        [s1 e1 s2 e2] (map read-string (rest l))]
                    [(first l) #(or (<= s1 % e1) (<= s2 % e2))])) lines)))

(defn parse-tickets
  [block]
  (map (fn [line]
         (map read-string (str/split line #","))) (rest block)))

(defn full-validator
  [data]
  (->> (parse-ranges (first data))
       (vals)
       (reduce (fn [c f] (fn [v] (or (c v) (f v)))) (constantly false))))

(defn find-invalid
  [data]
  (let [validator (full-validator data)]
    (->> (parse-tickets (last data))
         (mapcat identity)
         (filter (complement validator))
         (reduce +))))

(def part-1 (find-invalid data))
;; => 23954

(defn validate-ticket [checker ticket] (reduce #(and %1 (checker %2)) true ticket))

(defn find-indexes
  [data]
  (let [validator (full-validator data)
        by-field (->> (parse-tickets (last data)) ;; get ticket lines
                      (filter (partial validate-ticket validator)) ;; remove invalid
                      (apply map vector))] ;; transpose
    (->> (parse-ranges (first data)) ;; get ranges
         (map (fn [[nm checker]] ;; find columns for given field
                [nm (set (filter identity (map-indexed (fn [id c]
                                                         (when (validate-ticket checker c) id)) by-field)))]))
         (concat [["empty" #{}]])
         (sort-by (comp - count second)) ;; sort by size desc
         (partition 2 1) ;; group two 
         (map (fn [[[nm1 p1] [_ p2]]] [nm1 (first (difference p1 p2))])))))

(defn match-ranges
  [data]
  (let [my-ticket (vec (first (parse-tickets (second data))))]
    (->> (find-indexes data)
         (filter (comp #(str/starts-with? % "departure") first)) ;; filter departure only
         (map second) 
         (map my-ticket)
         (reduce *))))

(def part-2 (match-ranges data))
;; => 453459307723
