(ns advent-of-code-2024.day16
  (:require [common :refer [read-data ->priority-queue priority set-priority]]
            [clojure.set :as set]))

(defn starting-pos [data] [(- (count data) 2) 1 0 1])
(defn ending-pos [data] [1 (- (count data) 2)])

(defn neighbours [[x y dx dy]]
  [[[x y dy (- dx)] 1000]
   [[x y (- dy) dx] 1000]
   [[(+ x dx) (+ y dy) dx dy] 1]])

(defn traverse
  ([data] (let [start (starting-pos data)]
            (traverse data start (->priority-queue [[start 0]]) {start 0} {})))
  ([data start queue visited prev]
   (let [[cx cy :as state] (peek queue)]
     (if (= \E (get-in data [cx cy]))
       {:visited visited :end state :start start :paths prev}
       (let [curr-cost (priority queue state)
             [nqueue nvisited np] (reduce (fn [[q v p :as qvp] [[x y :as pos] cost]]
                                            (let [new-cost (+ curr-cost cost)]
                                              (cond
                                                (= \# (get-in data [x y])) qvp

                                                (and (v pos) (== (v pos) new-cost))
                                                [q v (update p pos conj state)]

                                                (v pos) qvp

                                                :else [(set-priority q pos new-cost)
                                                       (assoc v pos new-cost)
                                                       (update p pos conj state)])))
                                          [queue visited prev] (neighbours state))]
         (recur data start (pop nqueue) nvisited np))))))

(defn ->data [data] (traverse (mapv vec data)))
(def data (->data (read-data 2024 16)))

(defn find-best [{:keys [visited end]}] (visited end))

(def part-1 (find-best data))
;; => 135536

(defn find-comfortable
  ([{:keys [paths start end]}] (count (find-comfortable paths start end)))
  ([paths start [ex ey :as end]]
   (when (not= start end)
     (->> (paths end)
          (reduce (fn [buff ne]
                    (set/union buff (find-comfortable paths start ne)))
                  #{[ex ey]})))))

(def part-2 (find-comfortable data))
;; => 583

