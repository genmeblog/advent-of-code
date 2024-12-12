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
  (let [[[r1 c1] [r2 c2]] (seq fence)]
    (if (= r1 r2)
      (let [u (dec r1) d (inc r1)] [[[u c1] [u c2]] [[d c1] [d c2]]])
      (let [l (dec c1) r (inc c1)] [[[r1 l] [r2 l]] [[r1 r] [r2 r]]]))))

(defn side [fences fence]
  (let [[a b] (map fences (fence-neighbours fence))
        [nfences sa] (if a (side (disj fences a) a) [fences 0])
        [nfences sb] (if b (side (disj nfences b) b) [nfences 0])]
    [nfences (+ 1 sa sb)]))

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
                          (conj pairs [fields (sides fences)])]))) [#{} []])
         (second))))

(def data (separate (read-data 2024 12)))

(defn fencing-price [data method]
  (transduce (map (fn [[a b]] (* (count a) (method b)))) + data))

(def part-1 (fencing-price data sum))
;; => 1370258

(def part-2 (fencing-price data count))
;; => 805814
