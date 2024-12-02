(ns advent-of-code-2024.day02
  (:require [common :refer [read-data]]))

(defn parse-line [line] (mapv parse-long (re-seq #"\d+" line)))

(def data (map parse-line (read-data 2024 02)))

(defn diffs [report]
  (->> report
       (partition 2 1)
       (map (fn [[a b]] (- b a)))
       (set)))

(defn safe-report? [report-set]
  (or (every? #{1 2 3} report-set)
      (every? #{-1 -2 -3} report-set)))

(defn safe-data [data]
  (->> data
       (map diffs)
       (filter safe-report?)
       (count)))

(def part-1 (safe-data data))
;; => 269

(defn remove-item [v id]
  (into (subvec v 0 id)
        (subvec v (inc id))))

(defn safe-subreports? [report]
  (->> (range (count report))
       (map (partial remove-item report))
       (cons report)
       (safe-data)
       (pos?)))

(defn safe-data-2 [data]
  (->> data
       (filter safe-subreports?)
       (count)))

(def part-2 (safe-data-2 data))
;; => 337
