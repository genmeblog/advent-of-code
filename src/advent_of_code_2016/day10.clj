(ns advent-of-code-2016.day10
  (:require [common :refer [read-data parse]]
            [clojure.string :as str]))

(def re-value #"value (\d+) goes to bot (\d+)")
(def re-gives #"bot (\d+) gives low to (output|bot) (\d+) and high to (output|bot) (\d+)")

(defn parser
  [data]
  (reduce (fn [buff rule]
            (if (str/starts-with? rule "value")
              (let [[v b] (parse re-value rule)]
                (update-in buff [:state b] conj v))
              (let [[b l lv h hv] (parse re-gives rule)]
                (assoc-in buff [:rules b] {:low [(keyword l) lv]
                                           :high [(keyword h) hv]})))) {:state {} :rules {}} data))

(def data (parser (read-data 2016 10)))

(defn give-value
  [all v [whom id]]
  (let [k (if (= whom :bot) :state :output)]
    (update-in all [k id] conj v)))

(defn process
  [{:keys [rules] :as all} [who [one two]]]
  (let [[l h] (if (< one two) [one two] [two one])
        rule (rules who)
        res (-> (assoc-in all [:state who] nil)
                (give-value l (:low rule))
                (give-value h (:high rule)))]
    (if (and (= l 17) (= h 61))
      (assoc res :comparing who)
      res)))

(defn move
  [{:keys [state] :as all}]
  (let [d (filter #(= (count (second %)) 2) state)]
    (if (seq d)
      (reduce process all d)
      (assoc all :stop true))))

(def processed (iterate move data))

(defn search
  [processed what]
  (first (drop-while (complement what) processed)))

(def part-1 (:comparing (search processed :comparing)))
;; => 113

(defn outputs123
  [processed]
  (-> (search processed :stop)
      :output
      (select-keys [0 1 2])
      (->> (map (comp first second))
           (reduce *))))

(def part-2 (outputs123 processed))
;; => 12803
