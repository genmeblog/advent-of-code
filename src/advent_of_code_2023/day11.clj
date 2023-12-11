(ns advent-of-code-2023.day11
  (:require [common :refer [read-data]]))

(defn find-empty [lines]
  (->> (map-indexed vector lines)
       (reduce (fn [b [id l]]
                 (if (every? #{\.} l) (conj b id) b)) [])))

(defn galaxies [data]
  (for [r (range (count data))
        c (range (count (first data)))
        :when (= \# (get-in data [r c]))]
    [r c]))

(defn parse-data [data]
  {:gs (galaxies data)
   :er (find-empty data)
   :ec (find-empty (apply map vector data))})

(def data (parse-data (vec (read-data 2023 11))))

(defn count-extra-empty [a b e]
  (count (filter #(or (< a % b) (> a % b)) e)))

(defn distance [t er ec [r1 c1] [r2 c2]]
  (+ (abs (- r1 r2))
     (abs (- c1 c2))
     (* t (+ (count-extra-empty r1 r2 er)
             (count-extra-empty c1 c2 ec)))))

(defn distances [{:keys [gs er ec]} t]
  (->> (for [g1 gs g2 gs
             :when (pos? (compare g1 g2))]
         (distance t er ec g1 g2))
       (reduce +)))

(def part-1 (distances data 1))
;; => 9686930

(def part-2 (distances data 999999))
;; => 630728425490
