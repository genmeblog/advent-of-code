(ns advent-of-code-2020.day20
  (:require [common :refer [read-data-as-blocks]]
            [clojure.set :refer [intersection]]))

(defn parse-blocks
  [data]
  (sort-by :id (map (fn [block]
                      (let [id (last (re-find #"Tile (\d+):" (first block)))
                            r (rest block)
                            b [(seq (first r)) (map last r) (seq (last r)) (map first r)]
                            borders (concat b (map reverse b))]
                        {:id (read-string id)
                         :borders (set (concat b (map reverse b)))
                         :orientations (zipmap borders [:t :r :b :l :t- :r- :b- :l-])
                         :image (map (comp butlast rest) (butlast (rest r)))})) data)))

(def data (parse-blocks (read-data-as-blocks 2020 20)))

(defn find-orientations
  [data]
  (->> (for [b1 data
             b2 data
             :let [id1 (:id b1)
                   id2 (:id b2)
                   border1 (:borders b1)
                   border2 (:borders b2)
                   isction (intersection border1 border2)]
             :when (and (not= id1 id2)
                        (= 2 (count isction)))]
         [id1 id2 (map vector
                       (map (:orientations b1) isction)
                       (map (:orientations b2) isction))])
       (group-by first)))

(defn find-corners
  [data]
  (->> data
       (find-orientations)
       (filter (comp #(= 2 %) count second))
       (map first)))

(find-orientations data)

(def part-1 (reduce * (find-corners data)))
;; => 5966506063747

