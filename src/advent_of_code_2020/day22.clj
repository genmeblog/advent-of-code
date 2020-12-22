(ns advent-of-code-2020.day22
  (:require [common :refer [read-data-as-blocks]]))

(defn ->queue [xs] (into clojure.lang.PersistentQueue/EMPTY xs))

(defn parser
  [data]
  (->> data
       (rest)
       (map read-string)
       (->queue)))

(def data (mapv parser (read-data-as-blocks 2020 22)))

(defn step
  [winning-fn [l r visited]]
  (let [visited (or visited #{})
        sl (seq l) sr (seq r)
        id1 [:p1 sl] id2 [:p2 sr]]
    (if (or (visited id1) (visited id2))
      [nil :first]
      (if (and sl sr)
        (let [v1 (peek l) v2 (peek r)
              nl (pop l) nr (pop r)
              nvisited (conj visited id1 id2)]
          (if (winning-fn v1 v2 nl nr)
            [(conj nl v1 v2) nr nvisited]
            [nl (conj nr v2 v1) nvisited]))
        [(or sl sr) (if sl :first :second)]))))

(defn find-winner
  [winning-fn data]
  (->> data
       (iterate (partial step winning-fn))
       (drop-while (comp not keyword? second))
       (first)))

(defn game
  [data]
  (let [winner (first data)]
    (->> (range (count winner) 0 -1)
         (map * winner)
         (reduce +))))

(defn winning-regular  [v1 v2 _ _] (> v1 v2))

(def part-1 (game (find-winner winning-regular data)))
;; => 31629

(defn winning-recursive
  [v1 v2 l r]
  (if (and (<= v1 (count l))
           (<= v2 (count r)))
    (->> [(->queue (take v1 l))
          (->queue (take v2 r))]
         (find-winner winning-recursive)
         (second)
         (= :first))
    (> v1 v2)))

(def part-2 (game (find-winner winning-recursive data)))
;; => 35196
