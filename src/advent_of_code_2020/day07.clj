(ns advent-of-code-2020.day07
  (:require [common :refer [read-data]]))

(defn parse
  [line]
  (let [n (second (re-find #"^(\w+ \w+)" line))]
    (->> line
         (re-seq #"(\d+) (\w+ \w+) bag[s]*[\.,]")
         (map rest)
         (map (fn [[cnt nm]]
                [n nm (Integer/parseInt cnt)])))))

(def data (mapcat parse (read-data 2020 7)))

(defn traverse
  ([bags-map nm] (traverse bags-map [#{} #{}] nm))
  ([bags-map [traversed all :as buff] nm]
   (if (traversed nm)
     buff
     (let [names (map first (bags-map nm))]
       (reduce (partial traverse bags-map) [(conj traversed nm)
                                            (apply conj all names)] names)))))

(def part-1 (->> "shiny gold" (traverse (group-by second data)) second count))
;; => 296

(defn traverse2
  [data bag-name]
  (reduce (fn [^long total-count [_ nested-bag-name ^long bags-count]]
            (+ total-count
               bags-count
               (* bags-count (traverse2 data nested-bag-name)))) 0 (data bag-name)))


(def part-2 (traverse2 (group-by first data) "shiny gold"))
;; => 9339
