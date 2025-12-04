(ns advent-of-code-2025.day02
  (:require [common :refer [read-single-line]]
            [clojure.string :as str]
            [fastmath.core :as m]))

(defn parse-pair [pair] (map parse-long pair))

(defn parse-line
  [line]
  (->> (str/split line #"[\-\,]")
       (partition 2)
       (map parse-pair)))

(def data (parse-line (read-single-line 2025 2)))

(defn check-partition [s ^long cnt ^long i]
  (when (m/zero? (m/mod cnt i))
    (apply = (partition i s))))

(defn check-number
  [s ^long cnt]
  (when (m/> cnt 1)
    (some (partial check-partition s cnt) (range (m/quot cnt 2) 0 -1))))

(defn check-half [s ^long cnt]
  (when (m/even? cnt)
    (apply = (partition (m// cnt 2) s))))

(defn invalid
  [method [l r]]
  (->> (range l (inc r))
       (filter (fn [i] (let [s (str i)] (method s (count s)))))
       (reduce +)))

(defn all-invalid
  [data method]
  (->> (pmap (partial invalid method) data)
       (reduce +)))

(def part-1 (all-invalid data check-half))
;; => 23534117921

(def part-2 (all-invalid data check-number))
;; => 31755323497
