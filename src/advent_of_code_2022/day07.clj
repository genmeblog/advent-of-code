(ns advent-of-code-2022.day07
  (:require [common :refer [read-data split-line]]
            [clojure.string :as str]))

(defn add-listing
  [tree path listing]
  (reduce (fn [t line]                            
            (let [[a b] (split-line line)]
              (if (str/starts-with? line "d")
                (assoc-in t (conj path b) {})
                (update-in t (conj path :files) (fnil + 0) (parse-long a))))) tree listing))

(defn build-tree
  ([listing] (build-tree {} [] (rest listing)))
  ([tree path [line & rst]]
   (if line
     (if (str/starts-with? line "$ ls")
       (let [[listing rst] (split-with #(not (str/starts-with? % "$")) rst)]
         (recur (add-listing tree path listing) path rst))
       (let [f (last (split-line line))]
         (if (= ".." f)
           (recur tree (subvec path 0 (dec (count path))) rst)
           (recur tree (conj path f) rst))))
     tree)))

(defn du
  [tree]
  (reduce (fn [t [k v]]
            (let [nv (if (map? v) (du v) v)]
              (-> (assoc t k nv)
                  (update :size (fnil + 0) (or (:size nv) nv))))) tree tree))

(defn sizes
  [tree]
  (conj (->> (vals tree)
             (filter map?)
             (mapcat sizes)) (:size tree)))

(def data (-> (read-data 2022 7) build-tree du sizes))

(defn total-small-sizes
  [data]
  (reduce + (filter #(< % 100000) data)))

(def part-1 (total-small-sizes data))
;; => 1644735

(defn find-smallest-dir
  [data]
  (let [ts (- (first data) 40000000)]
    (reduce min (filter #(> % ts) data))))

(def part-2 (find-smallest-dir data))
;; => 1300850

