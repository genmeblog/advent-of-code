(ns advent-of-code-2018.day04
  (:require [common :refer [read-data]]
            [clojure.instant :as i]))

(defn log-parser
  "Parse input"
  [line]
  (let [[ydm h m op] (rest (re-find #"\[(\d+-\d+-\d+)\s(\d+):(\d+)\].*(#\d+|wakes|falls)" line))]
    {:date (i/read-instant-date (str ydm "T" h ":" m))
     :minute (Integer/parseInt m)
     :guard-id (when (= \# (first op))
                 (read-string (subs op 1)))}))

(defn pack-events
  "Reorganize input"
  [[id acc] {:keys [guard-id minute]}]
  (if-not guard-id
    [id (update acc id conj minute)]
    [guard-id acc]))

(def data (read-data 2018 4))

;; read whole log into a map
(defn log
  [data]
  (->> data
       (map log-parser)
       (sort-by :date)
       (reduce pack-events [-1 {}])
       (second)))

(defn time-stats
  [data]
  (for [[k v] (log data)
        :let [minutes (->> (reverse v)
                           (partition 2)
                           (mapcat #(apply range %)))
              [minute how-many] (->> minutes
                                     (frequencies)
                                     (sort-by val >)
                                     (first))]]
    [k how-many minute (count minutes)]))

(defn id-with-selector
  "Sort and calculate id using selected value from stats"
  [data selector]
  (let [[^long id _ ^long m] (first (sort-by selector > (time-stats data)))]
    (* id m)))

(def part-1 (id-with-selector data last))
;; => 20859

(def part-2 (id-with-selector data second))
;; => 76576
