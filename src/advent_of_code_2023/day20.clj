(ns advent-of-code-2023.day20
  (:require [common :refer [read-data]]
            [clojure.string :as str]))

(defn parse-line [l]
  (let [[from to] (str/split l #"\s->\s")]
    [(subs from 1) [(first from) (set (str/split to #", "))]]))

(defn find-all-sources [data k]
  (reduce (fn [b [s v]] (if ((second v) k) (assoc b s false) b)) {} data))

(defn build-state [data]
  (->> data
       (map (fn [[k [v]]] [k (condp = v \% false \& (find-all-sources data k) nil)]))
       (into {true 0 false 0})))

(defn flip-flop [state _ signal target]
  (when-not signal
    (let [v (not (state target))]
      [(assoc state target v) v])))

(defn conjuction [state source signal target]
  (let [nd (assoc (state target) source signal)]
    [(assoc state target nd) (not (reduce #(and %1 %2) (vals nd)))]))

(defn process-target [data [state nxt] [source signal target]]
  (let [[typ targets] (data target)
        state (update state signal inc)
        [nstate nsignal] (condp = typ
                           \% (flip-flop state source signal target)
                           \& (conjuction state source signal target)
                           [state signal])]
    (if (nil? nsignal)
      [(or nstate state) nxt]
      [nstate
       (->> targets
            (map vector (repeat target) (repeat nsignal))
            (reduce conj nxt))])))

(defn press-button
  ([data state] (press-button data state [["button" false "roadcaster"]]))
  ([data state nodes]
   (if-not (seq nodes)
     state
     (let [[nstate nnodes] (reduce (partial process-target data) [state []] nodes)]
       (recur data nstate nnodes)))))

(defn press-buttons [data]
  (let [parsed (->> data (map parse-line) (into {}))]
    (iterate (partial press-button parsed) (build-state parsed))))

(def data (press-buttons (read-data 2023 20)))

(defn pulses [data]
  (->> [true false]
       (select-keys (nth data 1000))
       (vals)
       (apply *)))

(def part-1 (pulses data))
;; => 879834312

(defn find-tick [data a b]
  (->> (map #(get % a) data)
       (reduce (fn [cnt m]
                 (if (m b) (reduced cnt) (inc cnt))) 0)))

(defn rx [data]
  (let [state (first data)]
    (->> ["jx" "cr" "vj" "nl"]
         (map #(->> (state %)
                    (keys)
                    (map (partial find-tick data %))
                    (reduce +)))
         (reduce *))))

(def part-2 (rx data))
;; => 243037165713371

;; graphviz
;;   dot -Tpng resources/202320.dot > images/advent_of_code_2023/day20.png

(-> "good morning" set sort)
;; => (\space \d \g \i \m \n \o \r)
