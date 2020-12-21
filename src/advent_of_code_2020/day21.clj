(ns advent-of-code-2020.day21
  (:require [common :refer [read-data]]
            [clojure.string :as str]
            [clojure.set :refer [intersection difference]]))

(defn parser
  [line]
  (let [[ingr all] (rest (re-find #"([a-z\s]+)\s\(contains ([a-z\s\,]+)\)" line))]
    {:ingredients (set (str/split ingr #" "))
     :allergens (str/split all #", ")}))

(def data (map parser (read-data 2020 21)))

(defn group-ingredients
  [data]
  (->> data
       (reduce (fn [m {:keys [ingredients allergens]}]
                 (reduce #(update %1 %2 conj ingredients) m allergens)) {})
       (map (fn [[k v]]
              [k (apply intersection v)]))))

(defn find-not-containing
  [data]
  (let [all-ingredients (mapcat :ingredients data)]
    (->> all-ingredients
         (filter (->> data
                      group-ingredients
                      (map second)
                      (reduce difference (set all-ingredients))))
         (count))))

(def part-1 (find-not-containing data))
;; => 1913

(defn order [in] (sort-by (comp count second) in))

(defn allergens
  [data]
  (loop [[[k a] & r] (order (group-ingredients data))
         buff []]
    (if k
      (recur (order (map (fn [[k v]]
                           [k (difference v a)]) r))
             (conj buff [k (first a)]))
      (->> buff
           (sort-by first)
           (map second)
           (str/join ",")))))

(def part-2 (allergens data))
;; => "gpgrb,tjlz,gtjmd,spbxz,pfdkkzp,xcfpc,txzv,znqbr"

