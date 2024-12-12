(ns advent-of-code-2024.day12
  (:require [common :refer [read-data neighbours4 sum]]
            [clojure.set :as set]))

(defn positions [data]
  (let [s (range (count data))]
    (for [row s col s] [row col])))

(defn surroundings [data pos]
  (->> (neighbours4 pos)
       (map (fn [p] [p (get-in data p)]))))

(defn spread
  ([data pos] (spread data pos [#{pos} #{}] (get-in data pos)))
  ([data pos field+fences c]
   (reduce (fn [[fd fs :as buff] [p c2]]
             (cond
               (fd p) buff
               (= c c2) (spread data p [(conj fd p) fs] c)
               :else [fd (conj fs [pos p])])) field+fences (surroundings data pos))))

(defn fence-neighbours [fence]
  (let [[f1 f2] fence] (map vector (neighbours4 f1) (neighbours4 f2))))

(defn side [fences fence]
  (let [ff (filter fences (fence-neighbours fence))]
    (reduce (fn [[fs cnt] f]
              (let [[nfs ncnt] (side (disj fs f) f)]
                [nfs (+ cnt ncnt)])) [fences 1] ff)))



(defn sides
  ([fences] (sides fences []))
  ([fences buff]
   (if-not (seq fences)
     buff
     (let [f (first fences)
           [nfences cnt] (side (disj fences f) f)]
       (recur nfences (conj buff cnt))))))

(defn separate [input]
  (let [data (mapv vec input)]
    (->> (positions data)
         (reduce (fn [[visited pairs :as buff] pos]
                   (if (visited pos) buff
                       (let [[fields fences] (spread data pos)]
                         [(set/union visited fields)
                          (conj pairs [(count fields) (sides fences)])]))) [#{} []])
         (second))))

(def data (separate (read-data 2024 12)))

(defn fencing-price [data method]
  (transduce (map (fn [[a b]] (* a (method b)))) + data))

(def part-1 (fencing-price data sum))
;; => 1370258

(def part-2 (fencing-price data count))
;; => 805814
