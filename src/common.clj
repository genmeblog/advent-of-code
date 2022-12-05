(ns common
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn- format-name
  ([year day] (format-name year day ""))
  ([year day suff]
   (format (str "advent_of_code_" year "/day%02d%s.txt") day suff)))

(defn read-single-line
  [& args]
  (-> (apply format-name args)
      (io/resource)
      (slurp)
      (str/trim)))

(defn read-data
  [& args]
  (-> (apply format-name args)
      (io/resource)
      (io/reader)
      (line-seq)))

(defn str-as-blocks
  ([s] (str-as-blocks s true))
  ([s trim?]
   (->> (str/split s  #"\n\n")
        (map (fn [block] (let [sb (str/split block #"\n")]
                          (if trim? (map str/trim sb) sb)))))))

(defn read-data-as-blocks
  [& args]
  (-> (apply format-name args)
      (io/resource)
      (slurp)
      (str-as-blocks)))

(defn read-data-as-blocks-no-trim
  [& args]
  (-> (apply format-name args)
      (io/resource)
      (slurp)
      (str-as-blocks false)))

(defn parse
  ([s] (mapv read-string s))
  ([re s] (mapv read-string (rest (re-find re s)))))

(defn split-line
  ([line] (split-line line #"\s+"))
  ([line re] (str/split (str/trim line) re)))
