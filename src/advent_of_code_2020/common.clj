(ns advent-of-code-2020.common
  (:require [clojure.java.io :as io]))

(defn read-data
  [day]
  (-> (io/resource (format "day%02d.txt" day))
      (io/reader)
      (line-seq)))
