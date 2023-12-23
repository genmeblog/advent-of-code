(ns advent-of-code-2023.day17
  (:require [common :refer [read-data ->priority-queue set-priority priority]]))

(defn parse-line [l] (mapv parse-long (re-seq #"\d" l)))

(def data (mapv parse-line (read-data 2023 17)))

(defn add [[a b] [c d]] [(+ a c) (+ b d)])
(defn left [pos [dx dy]] (let [ndir [dy (- dx)]] [(add pos ndir) ndir 1]))
(defn right [pos [dx dy]] (let [ndir [(- dy) dx]] [(add pos ndir) ndir 1]))

(defn neighbours [pos dir dist mn mx]
  (as-> [] ns
    (if (>= dist mn) [(left pos dir) (right pos dir)] ns)
    (if (< dist mx) (conj ns [(add pos dir) dir (inc dist)]) ns)))

(defn find-path
  ([data mn mx] (let [x (dec (count data))
                      y (dec (count (first data)))]
                  (find-path data [0 0] [x y] mn mx)))
  ([data start end mn mx]
   (find-path data end (-> (->priority-queue)
                           (set-priority [start [0 1] 1] 0)
                           (set-priority [start [1 0] 1] 0)) {} mn mx))
  ([data [ex ey :as end] Q visited mn mx]
   (let [[curr dir dist :as state] (peek Q)]
     (if (and (= curr end) (>= dist mn))
       (visited state)
       (let [curr-cost (priority Q state)
             [nQ nvisited] (reduce (fn [[q v :as qv] [[nx ny :as npos] :as n]]
                                     (if (or (visited n) (neg? nx) (neg? ny) (> nx ex) (> ny ey))
                                       qv
                                       (let [new-cost (+ curr-cost (get-in data npos))]
                                         [(set-priority q n new-cost)
                                          (assoc v n new-cost)])))
                                   [Q visited] (neighbours curr dir dist mn mx))]
         (recur data end (pop nQ) nvisited mn mx))))))

(defonce part-1 (find-path data 1 3))
;; => 1013

(defonce part-2 (find-path data 4 10))
;; => 1215
