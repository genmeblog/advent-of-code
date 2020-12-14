(ns advent-of-code-2020.day14
  (:require [common :refer [read-data]]
            [clojure.string :as str]))

(def data (read-data 2020 14))

(defn ->or-mask [v] (-> (str/replace v "X" "0") (Long/parseLong 2)))
(defn ->and-mask [v] (-> (str/replace v #"[01]" "0")
                         (str/replace "X" "1")
                         (Long/parseLong 2)))

(defn apply-masks-to-data
  [[mem and-mask or-mask :as b] line]
  (let [[cmd v] (str/split line #" = ")]
    (if (= "mask" cmd)
      [mem (->and-mask v) (->or-mask v)]
      (update b 0 assoc cmd (-> (read-string v)
                                (bit-and and-mask)
                                (bit-or or-mask))))))

(defn memory-sum
  [f data]
  (->> data
       (reduce f [{} 1 0])
       (first)
       (vals)
       (reduce +)))

(def part-1 (memory-sum apply-masks-to-data data))
;; => 14839536808842

;;

(defn make-mask-fn
  [mask]
  (let [vor (->or-mask mask)
        vnand (bit-not (->and-mask mask))]
    (fn [addr] (bit-and (bit-or addr vor) vnand))))

(defn mask-positions
  [mask]
  (->> mask
       (map-indexed #(when (= %2 \X) (- 35 %1)))
       (filter identity)
       (reverse)))

(defn addresses
  [positions mask-fn addr]
  (for [v (->> positions count (bit-shift-left 1) range)]
    (first (reduce (fn [[addr b] pos]
                     [(if (even? b) (bit-set addr pos) (bit-clear addr pos))
                      (bit-shift-right b 1)])
                   [(mask-fn addr) v] positions))))

(defn apply-masks-to-address
  [[mem positions mask-fn :as b] line]
  (let [[cmd v] (str/split line #" = ")]
    (if (= "mask" cmd)
      [mem (mask-positions v) (make-mask-fn v)]
      (let [addr (-> (re-find #"mem\[(\d+)\]" line) second read-string)
            addrs (addresses positions mask-fn addr)]
        (assoc b 0 (reduce #(assoc %1 %2 (read-string v)) mem addrs))))))

(def part-2 (memory-sum apply-masks-to-address data))
;; => 4215284199669

