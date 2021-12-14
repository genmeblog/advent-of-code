(ns advent-of-code-2021.day14
  (:require [common :refer [read-data-as-blocks]]
            [clojure.string :as str]))

(defn parser
  [[[template] rules]]
  {:template {:pairs (frequencies (partition 2 1 template))
              :chars (frequencies template)}
   :rules (reduce #(let [[[l r] [c]] (str/split %2 #" -> ")]
                     (assoc %1 [l r] [[l c] [c r]])) {} rules)})

(def data (parser (read-data-as-blocks 2021 14)))

(defn polymer-grow
  [rules template]
  (reduce (fn [current-template [p cnt]]
            (let [[l r] (rules p)]
              (->> [[:pairs l] [:pairs r] [:chars (first r)]]
                   (reduce #(update-in %1 %2 (fnil + 0) cnt) current-template))))
          (dissoc template :pairs)
          (:pairs template)))

(defn nth-polymer-data
  [{:keys [rules template]} n]
  (->> (-> (partial polymer-grow rules)
           (iterate template)
           (nth n)
           (:chars)
           (vals)) 
       (apply (juxt max min))
       (reduce -)))

(def part-1 (nth-polymer-data data 10))
;; => 2194

(def part-2 (nth-polymer-data data 40))
;; => 2360298895777
