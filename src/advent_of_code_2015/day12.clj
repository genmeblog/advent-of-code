(ns advent-of-code-2015.day12
  (:require [common :refer [read-single-line]]
            [clojure.data.json :as json]))

(def json-str (read-single-line 2015 12))

(defn count-numbers [jstr]
  (->> (re-seq #"\-?\d+" jstr)
       (map parse-long)
       (reduce +)))

(def part-1 (count-numbers json-str))
;; => 156366

(def json (json/read-str json-str))

