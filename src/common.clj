(ns common
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn- format-name
  [year day]
  (format (str "advent_of_code_" year "/day%02d.txt") day))

(defn read-single-line
  [year day]
  (-> (format-name year day)
      (io/resource)
      (slurp)
      (str/trim)))

(defn read-data
  [year day]
  (-> (format-name year day)
      (io/resource)
      (io/reader)
      (line-seq)))

(defn str-as-blocks
  [s]
  (->> (str/split s  #"\n\n")
       (map #(str/split % #"\n"))))

(defn read-data-as-blocks
  [year day]
  (-> (format-name year day)
      (io/resource)
      (slurp)
      (str-as-blocks)))

(defn parse
  [re s]
  (map read-string (rest (re-find re s))))
