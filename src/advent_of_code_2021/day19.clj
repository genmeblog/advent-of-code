(ns advent-of-code-2021.day19
  (:require [common :refer [read-data-as-blocks parse]]
            [fastmath.vector :as v]
            [clojure.set :as set]))

(defn parse-block
  [block]
  (->> (rest block)
       (map #(str "[" % "]"))
       (parse)))

(defn parser
  [data]
  (mapv parse-block data))

(def data (parser (read-data-as-blocks 2021 19 "example")))

(defn distances
  [block]
  (update-vals (->> (for [a block
                          b block
                          :when (not= a b)]
                      [a (v/dist-abs a b)])
                    (group-by first))
               (comp set #(map second %))))

(def pairs (for [
               [p1 d1] (distances (nth data 1))
               [p2 d2] (distances (nth data 0))
               :when (>= (count (set/intersection d1 d2)) 10)]
           [p1 p2]))



