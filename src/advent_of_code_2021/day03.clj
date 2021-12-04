(ns advent-of-code-2021.day03
  (:require [common :refer [read-data]]))

(def data (read-data 2021 3))

(defn power-consumption
  [data mask]
  (let [gamma (Integer/parseInt (->> (apply map vector data)
                                     (map frequencies)
                                     (map (partial sort-by second))
                                     (map ffirst)
                                     (apply str)) 2)
        epsilon (->> gamma bit-not (bit-and mask))]
    (* gamma epsilon)))

(def part-1 (power-consumption data 0xfff))
;; => 2967914

(defn apply-bit-criteria
  ([data selector] (apply-bit-criteria data selector 0))
  ([data [id1 id2 cmp :as selector] bit]
   (if (= (count data) 1)
     (Integer/parseInt (first data) 2)
     (let [gdata (group-by #(nth % bit) data)
           [d1 d2] [(gdata id1) (gdata id2)]]
       (recur (if (cmp (count d1) (count d2)) d1 d2) selector (inc bit))))))

(defn life-support-rating
  [data]
  (* (apply-bit-criteria data [\1 \0 <])
     (apply-bit-criteria data [\0 \1 >])))

(def part-2 (life-support-rating data))
;; => 7041258
