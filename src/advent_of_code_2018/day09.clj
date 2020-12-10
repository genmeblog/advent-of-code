(ns advent-of-code-2018.day09
  (:require [clojure.java.io :as io])
  (:import [java.util LinkedList]))

(set! *unchecked-math* :warn-on-boxed)
(set! *warn-on-reflection* true)

(def game-data (delay (mapv read-string (rest (re-find #"(\d+) player.+ (\d+) points" (slurp (io/resource "day09.txt")))))))

(defprotocol MarbleListProto
  (insert-at+2 [l v])
  (remove-at-7 [l])
  (add [l v])
  (delete [l])
  (current [l])
  (fd [l])
  (bk [l])
  (swap [l]))

(deftype MarbleList [^LinkedList left ^LinkedList right]
  Object
  (toString [_] (str left " - " right))
  MarbleListProto
  (add [t v] (do (.addLast left v) t))
  (swap [t] (if (zero? (.size left)) (MarbleList. right left) t))
  (delete [t] (do (.removeLast left) (swap t)))
  (current [_] (.getLast left))
  (fd [t] (if (zero? (.size right))
            (do (.addFirst right (.getFirst left)) (.removeFirst left) (MarbleList. right left))
            (do (.addLast left (.getFirst right)) (.removeFirst right) t)))
  (bk [t] (do (.addFirst right (.getLast left)) (.removeLast left) (swap t)))
  (insert-at+2 [t v] (-> t fd (add v)))
  (remove-at-7 [t]
    (let [shifted (-> t bk bk bk bk bk bk bk)]
      [(current shifted) (-> shifted delete fd)])))

(defn marble-list []
  (MarbleList. (LinkedList. [0]) (LinkedList.)))

(defn marble-game
  [^long players ^long max-marble]
  (fn [^long id lst points]
    (when (zero? (rem id 100000)) (println id))
    (if (> id max-marble)
      (apply max (vals points))
      (if (zero? (rem id 23))
        (let [[pts lst] (remove-at-7 lst)]
          (recur (inc id) lst (update points (rem (dec id) players) + id pts)))
        (recur (inc id) (insert-at+2 lst id) points)))))

(defn empty-map [players]
  (into {} (map vector (range players) (repeat 0))))

(time
 (let [players (first @game-data)
       ^long max-marble (second @game-data)]
   {:max-score ((marble-game players max-marble) 1 (marble-list) (empty-map players))
    :max-score-100 ((marble-game players (* 100 max-marble)) 1 (marble-list) (empty-map players))}))
;; => {:max-score 422748, :max-score-100 3412522480}

