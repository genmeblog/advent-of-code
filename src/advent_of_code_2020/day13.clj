(ns advent-of-code-2020.day13
  (:require [common :refer [read-data]]
            [clojure.string :as str]))

(set! *warn-on-reflection* true)

(defn parse
  [data]
  (let [ts (read-string (first data))
        buses (->> (str/split (second data) #",")
                   (map read-string)
                   (map-indexed vector)
                   (remove (comp #{'x} second)))]
    {:timestamp ts
     :buses buses}))

(def data (parse (read-data 2020 13)))

(defn nearest-depart
  [{:keys [^long timestamp buses]}]
  (->> (map second buses)
       (map (fn [^long b] (vector b (- b (mod timestamp b)))))
       (sort-by second)
       (first)
       (apply *)
       (long)))

(def part-1 (nearest-depart data))
;; => 4808

;;

;; align buses pairwise

(defn process
  [{:keys [buses]}]
  (->> buses
       (map (fn [[id bus]] [(- bus (mod id bus)) bus]))
       (reduce (fn [[offset1 bus1] [offset2 bus2]]
                 [(loop [res (bigint offset1)]
                    (if (= (mod res bus2) offset2)
                      res
                      (recur (+ res bus1))))
                  (* bus1 bus2)]))
       (first)))

(def part-2 (time (process data)))
;; => 741745043105674
