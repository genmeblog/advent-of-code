(ns advent-of-code-2022.day07
  (:require [common :refer [read-data split-line]]
            [clojure.string :as str]))

(defn get-size
  [listing]
  (->> listing
       (map (comp first split-line))
       (remove #{"dir"})
       (map parse-long)
       (reduce +)))

(defn build-tree
  ([listing] (build-tree {} [] (rest listing)))
  ([tree path [line & rst]]
   (if line
     (if (str/starts-with? line "$ ls")
       (let [[listing rst] (split-with #(not (str/starts-with? % "$")) rst)]
         (recur (assoc-in tree (conj path :files) (get-size listing)) path rst))
       (let [f (last (split-line line))]
         (if (= ".." f)
           (recur tree (subvec path 0 (dec (count path))) rst)
           (recur tree (conj path f) rst))))
     tree)))

(defn sizes
  [tree]
  (conj (->> (vals tree)
             (filter map?)
             (mapcat sizes))
        (->> (tree-seq map? vals tree)
             (remove map?)
             (reduce +))))

(def data (-> (read-data 2022 7) build-tree sizes))

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
