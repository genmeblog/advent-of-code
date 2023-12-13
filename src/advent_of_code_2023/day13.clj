(ns advent-of-code-2023.day13
  (:require [common :refer [read-data-as-blocks]]))

(def data (read-data-as-blocks 2023 13))

(defn equal? [[a b]] (= a b))
(defn mirrored? [pair] (every? equal? pair))
(defn orpair [[a b]] (or a b))
(defn reverse-a [[a b]] (map vector (reverse a) b))

(defn diff-sum [[a b]]
  (->> (map (fn [c1 c2] (if (= c1 c2) 0 1)) a b)
       (reduce +)))

(defn mirrored-but-one? [pair]
  (let [c (map (juxt equal? diff-sum) pair)]
    (and (every? orpair c)
         (= 1 (reduce + (map second c))))))

(defn id-of [block m? rows id]
  (when (->> block(split-at id) reverse-a m?)
    (* rows id)))

(defn find-id [block m? rows]
  (->> (count block)
       (range 1)
       (some (partial id-of block m? rows))))

(defn find-id-all [m? block]
  (or (find-id block m? 100)
      (find-id (apply map vector block) m? 1)))

(defn solution [data m?]
  (->> (map (partial find-id-all m?) data)
       (reduce +)))

(def part-1 (solution data mirrored?))
;; => 31877

(def part-2 (solution data mirrored-but-one?))
;; => 42996
