(ns advent-of-code-2023.day15
  (:require [common :refer [read-single-line]]
            [clojure.string :as str]))

(defn parse [data] (str/split data #","))

(def data (parse (read-single-line 2023 15)))

(defn HASH [token] (reduce (fn [v c] (bit-and 0xff (* 17 (+ v (int c))))) 0 token))

(def part-1 (reduce + (map HASH data)))
;; => 511498

(defn maybe-replace? [lst l fl]
  (reduce (fn [[buff r?] [cl :as c]]
            (if (= cl l)
              [(conj buff [l fl]) true]
              [(conj buff c) r?])) [[] false] lst))

(defn add-or-replace-lens [lst l fl]
  (let [[nlst r?] (maybe-replace? lst l fl)]
    (if r? nlst (conj nlst [l fl]))))

(defn remove-lens [lst l _]
  (reduce (fn [buff [cl :as c]]
            (if (= cl l) buff (conj buff c))) [] lst))

(defn init-seq [data]
  (-> (reduce (fn [buff chunk]
                (let [fl (-> chunk last str read-string)
                      l (apply str (butlast chunk))]
                  (update buff (HASH l) (if (number? fl)
                                          add-or-replace-lens
                                          remove-lens) l fl)))
              (vec (repeat 256 [])) data)))

(defn process-box [box-id lst]
  (map-indexed (fn [id1 [_ fl]] (* (inc box-id) (inc id1) fl)) lst))

(defn total-focusing-power [data]
  (->> (map #(str/escape % {\= ""}) data)
       (init-seq)
       (mapcat (fn [id lst] (process-box id lst)) (range))
       (reduce +)))

(def part-2 (total-focusing-power data))
;; => 284674
