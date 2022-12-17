(ns advent-of-code-2022.day16
  (:require [common :refer [read-data bfs->path-count]]))

(defn parse
  [line]
  (let [[start flow & rst] (re-seq #"[A-Z]{2}|\d+" line)]
    [start {:flow (parse-long flow)
            :target (set rst)}]))

(defn path-len [data start end] (bfs->path-count (comp :target data) start end))

(defn distances
  [data targets]
  (let [points (conj targets "AA")]
    (into {} (for [s points
                   t points
                   :when (not= s t)]
               [[s t] (inc (path-len data s t))]))))

(defn parse-data
  [in]
  (let [data (into {} (map parse in))
        targets (->> data
                     (filter (comp pos? :flow second))
                     (map first)
                     (set))]
    (assoc data :distances (distances data targets) :targets targets)))

(def data (parse-data (read-data 2022 16)))

(defn traverse
  ([data] (traverse data (:targets data) "AA" 30))
  ([{:keys [distances] :as data} targets start minutes]
   (if (seq targets)
     (reduce (fn [curr t]
               (max curr (let [rst (- minutes (distances [start t]))]
                           (if (pos? rst)
                             (+ (* rst (:flow (data t)))
                                (traverse data (disj targets t) t rst))
                             0)))) 0 targets)
     0)))

(def part-1 (traverse data))
;; => 2080

(defn traverse2
  ([data] (traverse2 data (:targets data) "AA" 26 "AA" 26))
  ([{:keys [distances] :as data} targets start1 minutes1 start2 minutes2]
   (if (seq targets)
     (reduce (fn [curr t]
               (max curr (let [rst (- minutes1 (distances [start1 t]))]
                           (if (pos? rst)
                             (+ (* rst (:flow (data t)))
                                (if (> rst minutes2)
                                  (traverse2 data (disj targets t) t rst start2 minutes2)
                                  (traverse2 data (disj targets t) start2 minutes2 t rst)))
                             0)))) 0 targets)
     0)))


;; so slow...
;; (defonce part-2 (traverse2 data))
;; => 2752
