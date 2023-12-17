(ns advent-of-code-2023.day16
  (:require [common :refer [read-data]]))

(defn parse [data] {:m (vec data) :size (count data)})

(def data (parse (read-data 2023 16)))

(defn move-ray [[row col] dir]
  [(case dir
     :u [(dec row) col]
     :d [(inc row) col]
     :l [row (dec col)]
     :r [row (inc col)]) dir])

(defn next-ray [m pos dir rst]
  (->> (condp = (get-in m pos)
         \| (if (#{:r :l} dir)
              [(move-ray pos :u) (move-ray pos :d)]
              [(move-ray pos dir)])
         \- (if (#{:u :d} dir)
              [(move-ray pos :l) (move-ray pos :r)]
              [(move-ray pos dir)])
         \\ [(move-ray pos ({:l :u :r :d :u :l :d :r} dir))]
         \/ [(move-ray pos ({:l :d :r :u :u :r :d :l} dir))]
         [(move-ray pos dir)])
       (apply conj rst)))

(defn outside? [[row col] size]
  (or (neg? row) (neg? col) (= size row) (= size col)))

(defn move-rays
  ([data] (move-rays data [[0 0] :r]))
  ([{:keys [m size]} ray] (move-rays m size [ray] #{}))
  ([m size [[pos dir :as r] & rst :as rays] visited]
   (cond
     (empty? rays) (->> visited (map first) distinct count)
     (or (visited r) (outside? pos size)) (recur m size rst visited)
     :else (recur m size (next-ray m pos dir rst) (conj visited r)))))

(def part-1 (move-rays data))
;; => 8116

(defn find-max [{:keys [size] :as data}]
  (let [size- (dec size)]
    (->> (range size)
         (pmap (fn [i] (max (move-rays data [[i 0] :r])
                           (move-rays data [[i size-] :l])
                           (move-rays data [[0 i] :d])
                           (move-rays data [[size- i] :u]))))
         (reduce max))))

(def part-2 (find-max data))
;; => 8383
