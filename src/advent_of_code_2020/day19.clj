(ns advent-of-code-2020.day19
  (:require [common :refer [read-data-as-blocks]]
            [clojure.string :as str]))

(def data (read-data-as-blocks 2020 19))

(defn build-tree
  [block]
  (into {} (map (fn [line]
                  (let [[id r] (-> #"(\d+)\:\s(.*)"
                                   (re-find (str/escape line {\" ""}))
                                   (rest))
                        ors (str/split r #" \| ")]
                    [id (map #(str/split % #" ")ors)])) block)))

(defn build-regex
  [tree id]
  (let [[[l1 r1] [l2 r2]] (tree id)]
    (str "(:?"
         (if (#{"a" "b"} l1)
           l1
           (str (build-regex tree l1)
                (when (seq r1) (str (build-regex tree r1)))))
         (when (seq l2)
           (str "|" (build-regex tree l2)
                (when (seq r2) (build-regex tree r2))))
         ")")))

(defn regex1
  [data]
  (re-pattern (build-regex (build-tree data) "0")))

(defn regex2
  [data]
  (re-pattern (let [tr (build-tree data)
                    r31 (build-regex tr "31")
                    r42 (build-regex tr "42")]
                (str "(:?" r42 "+)"
                     "(:?" r42 r31 "|" r42 "{2}" r31 "{2}"
                     "|" r42 "{3}" r31 "{3}" "|" r42 "{4}" r31 "{4}"
                     "|" r42 "{5}" r31 "{5}"")"))))

(defn matches
  [data regex]
  (count (filter #(re-matches (regex (first data)) %) (second data))))


(def part-1 (matches data regex1))
;; => 178

(def part-2 (matches data regex2))
;; => 346
