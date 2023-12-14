(ns advent-of-code-2023.day14
  (:require [common :refer [read-data]]))

(defn rotate [block] (reverse (apply map str block)))

(defn tilt [l]
  (->> (re-seq #"\#+|[.O]+" l)
       (mapcat (comp reverse sort))))

(defn tilt-all [block] (map tilt block))

(defn cycle-tilts [data]
  (-> data tilt-all rotate tilt-all rotate tilt-all rotate tilt-all rotate))

(defn score [l]
  (->> (reverse l)
       (map-indexed (fn [id ch] (if (= ch \O) (inc id) 0)))))

(defn score-all [data]
  (->> (mapcat score data)
       (reduce +)))

(def data (apply map str (read-data 2023 14)))

(def part-1 (score-all (tilt-all data)))
;; => 109833

(defn find-cycle-and-return [target [init curr] b]
  (if (curr b)
    (if (zero? init)
      [(count curr) #{b}]
      (let [n (mod (- target init) (count curr))]
        (reduced (nth (iterate cycle-tilts b) n))))
    [init (conj curr b)]))

(defn nth-tilt [data target]
  (reduce (partial find-cycle-and-return target) [0 #{}] (iterate cycle-tilts data)))

(defonce part-2 (score-all (nth-tilt data 1000000000)))
;; => 99875
