(ns advent-of-code-2020.day20
  (:require [common :refer [read-data-as-blocks]]))

(defn parse-blocks
  [data]
  (sort-by :id (map (fn [block]
                      (let [id (last (re-find #"Tile (\d+):" (first block)))
                            r (rest block)
                            b [(seq (first r)) (map last r) (seq (last r)) (map first r)]]
                        {:id (read-string id)
                         :borders (set (concat b (map reverse b)))
                         :image (map (comp butlast rest) (butlast (rest r)))})) data)))

(def data (parse-blocks (read-data-as-blocks 2020 20)))

(first data )

(reduce * (map first (filter (comp #(= 2 (count %)) second) (group-by first (for [b1 data
                                                                                  b2 data
                                                                                  :let [id1 (:id b1)
                                                                                        id2 (:id b2)]
                                                                                  :when (not= id1 id2)
                                                                                  :let [cnt (count (clojure.set/intersection (:borders b1) (:borders b2)))]
                                                                                  :when (pos? cnt)]
                                                                              [(:id b1) (:id b2) cnt])))))
;; => 5966506063747
