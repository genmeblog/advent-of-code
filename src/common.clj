(ns common
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn read-data
  [year day]
  (-> (io/resource (format (str "advent_of_code_" year "/day%02d.txt") day))
      (io/reader)
      (line-seq)))

(defn str-as-blocks
  [s]
  (->> (str/split s  #"\n\n")
       (map #(str/split % #"\n"))))

(defn read-data-as-blocks
  [year day]
  (-> (io/resource (format (str "advent_of_code_" year "/day%02d.txt") day))
      (slurp)
      (str-as-blocks)))

