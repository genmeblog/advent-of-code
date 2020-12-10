(ns advent-of-code-2018.day07
  (:require [clojure.java.io :as io]
            [clojure.set :refer :all]))

(set! *unchecked-math* :warn-on-boxed)
(set! *warn-on-reflection* true)

(def coords (->> "day07.txt"
                 (io/resource)
                 (io/reader)
                 (line-seq)
                 (map #(re-find #"Step\s([A-Z]).*step\s([A-Z])" %))
                 (map rest)
                 (mapv #(let [[s1 s2] %]
                          [(first s1) (first s2)]))
                 (delay)))

(defn edges->map [f1 f2] (into {} (map (fn [[k v]]
                                         [k (set (map f2 v))]) (group-by f1 @coords))))

(def all (set (flatten @coords)))
(def starts (edges->map first second))
(def ends (edges->map second first))
(def starting (apply sorted-set (difference all (set (keys ends)))))

(defn remove-ends
  [start ends]
  (reduce #(update %1 %2 disj start) ends (starts start)))

(defn empty-ends
  [ends]
  (->> ends
       (filter (comp empty? second))
       (map first)))

(defn process-ends
  [curr-string candidates ends]
  (let [next-char (first candidates)
        new-ends-pre (remove-ends next-char ends)
        new-candidates (empty-ends new-ends-pre)
        curr-candidates (apply conj (disj candidates next-char) new-candidates)]
    (if (seq curr-candidates)
      (recur (str curr-string next-char) curr-candidates (apply dissoc new-ends-pre new-candidates))
      (str curr-string next-char))))

(defn fill-workers
  [candidates workers]
  (if (and (seq candidates) (< (count workers) 5))
    (recur (rest candidates) (conj workers [(first candidates) (- (int (first candidates)) 4)]))
    [candidates workers]))

(apply min-key second (second (fill-workers starting [])))

(defn calc-time
  [^long time workers]
  (let [[_ ^long v] (apply min-key second workers)]
    [(+ time v) (reduce (fn [lst [ch ^long cval]]
                          (conj lst [ch (- cval v)])) [] workers)]))

(defn process-ends-parallel
  [curr-string candidates ends workers time]
  (let [[ncandidates nworkers] (fill-workers candidates workers) ;; should be sorted map!!! (keys act as candidates)
        [ntime nworkers] (calc-time time nworkers)
        ;; remove ends (possibly multiple)
        ;; add new candidates
        ;; ...
        nworkers (remove (comp zero? second) nworkers)
        ]
    ))

(time {:order (process-ends "" starting ends)})
;; => {:order "OVXCKZBDEHINPFSTJLUYRWGAMQ"}
