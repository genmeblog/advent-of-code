(ns advent-of-code-2021.day25
  (:require [common :refer [read-data]]))

(defn nxt [off id pos] (update pos id #(mod (inc %) off)))

(defn build-set
  [w h d target]
  (set (for [x (range w)
             y (range h)
             :when (= target (get-in d [y x]))]
         [x y])))

(defn parse
  [data]
  (let [d (mapv vec data)
        w (count (first d))
        h (count d)]
    {:nxt [(partial nxt w 0)
           (partial nxt h 1)]
     :positions [(build-set w h d \>)
                 (build-set w h d \v)]}))

(def data (parse (read-data 2021 25)))

(defn step
  [nxt positions positions2]
  (set (map #(let [n (nxt %)]
               (if (or (positions n) (positions2 n)) % n)) positions)))


(defn iterator
  [{[f1 f2] :nxt [p1 p2] :positions :as data}]
  (let [n1 (step f1 p1 p2)
        n2 (step f2 p2 n1)]
    (assoc data :positions [n1 n2])))


(defn move-until-stop
  [data]
  (->> data
       (iterate iterator)
       (partition 2 1)
       (take-while #(not= (first %) (second %)))
       (count)
       (inc)))

(defonce part-1 (move-until-stop data))
;; => 458
