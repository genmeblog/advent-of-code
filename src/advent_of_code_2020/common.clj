(ns advent-of-code-2020.common
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn read-data
  [day]
  (-> (io/resource (format "day%02d.txt" day))
      (io/reader)
      (line-seq)))

(defn str-as-blocks
  [s]
  (->> (str/split s  #"\n\n")
       (map #(str/split % #"\n"))))

(defn read-data-as-blocks
  [day]
  (-> (io/resource (format "day%02d.txt" day))
      (slurp)
      (str-as-blocks)))

