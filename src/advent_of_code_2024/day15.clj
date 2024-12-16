(ns advent-of-code-2024.day15
  (:require [common :refer [read-data-as-blocks addv sum]]))

(defn find-start
  ([warehouse rng] (find-start warehouse rng rng))
  ([warehouse rng1 rng2]
   (first (for [row rng1 col rng2
                :when (= \@ (get-in warehouse [row col]))]
            [row col]))))

(defn parse-data [[warehouse moves]]
  (let [w (mapv vec warehouse)
        rng (range (count w))]
    {:warehouse w
     :moves (apply str moves)
     :start (find-start w rng)
     :rng rng}))

(def dirs {\^ [-1 0] \v [1 0] \> [0 1] \< [0 -1]})

(def data (parse-data (read-data-as-blocks 2024 15)))

(defn move [warehouse pos dir start]
  (let [npos (addv pos dir)]
    (condp = (get-in warehouse npos)
      \O (move warehouse npos dir start)
      \# [warehouse start]
      (let [np (addv start dir)]
        [(-> warehouse
             (assoc-in npos \O)
             (assoc-in start \.)
             (assoc-in np \@))
         np]))))

(defn coordinates [warehouse c rng1 rng2]
  (sum (for [row rng1 col rng2
             :when (= c (get-in warehouse [row col]))]
         (+ col (* 100 row)))))

(defn move-all [{:keys [warehouse moves start rng]}]
  (-> (reduce (fn [[wh pos] dir]
                (move wh pos (dirs dir) pos)) [warehouse start] moves)
      (first)
      (coordinates \O rng rng )))

(def part-1 (move-all data))
;; => 1476771

(def subst {\O "[]" \. ".." \# "##" \@ "@."})

(defn resize [warehouse-line]
  (vec (mapcat subst warehouse-line)))

(defn substitute [{:keys [warehouse rng moves]}]
  (let [wh (mapv resize warehouse)
        rng2 (range (count (first wh)))]
    {:warehouse wh
     :moves moves
     :rng1 rng
     :rng2 rng2
     :start (find-start wh rng rng2)}))

(defn gather-stones [r cols]
  (mapcat (fn [v]
            (condp = (r v)
              \[ [v (inc v)]
              \] [(dec v) v]
              [])) cols))

(defn move-vertical1 [warehouse cols row nrow]
  (let [cr (warehouse row)]
    (reduce (fn [wh col]
              (-> wh
                  (assoc-in [nrow col] (cr col))
                  (assoc-in [row col] \.))) warehouse cols)))

(defn move-vertical [warehouse row cols dir]
  (let [nrow (+ row dir)
        r (warehouse nrow)
        v (map r cols)]
    (if (some #(= \# %) v)
      [false warehouse]
      (if-let [ncols (seq (gather-stones r cols))]
        (let [[move? wh :as curr] (move-vertical warehouse nrow ncols dir)]
          (if-not move?
            curr
            [move? (move-vertical1 wh cols row nrow)]))
        [true (move-vertical1 warehouse cols row nrow)]))))

(defn move-horizontal1 [row col ncol]
  (-> row
      (assoc ncol (row col))
      (assoc col \.)))

(defn move-horizontal-row [row col dir]
  (let [ncol (+ col dir)]
    (cond
      (= \# (row ncol)) [false row]
      (= \. (row ncol)) [true (move-horizontal1 row col ncol)]
      :else (let [[move? r :as curr] (move-horizontal-row row ncol dir)]
              (if-not move?
                curr
                [move? (move-horizontal1 r col ncol)])))))

(defn move-horizontal [warehouse [row col] dir]
  (let [[move? r] (move-horizontal-row (warehouse row) col dir)]
    [move? (assoc warehouse row r)]))

(def edata (parse-data (read-data-as-blocks "examples" 2024 15)))

(def dirs2 {\< -1 \> 1 \^ -1 \v 1})

(defn move-all-2 [edata]
  (let [{:keys [warehouse moves rng1 rng2 start]} (substitute edata)]
    (-> (reduce (fn [[wh [row col :as pos]] dir]
                  (let [[move? nwh] (if (#{\< \>} dir)
                                      (move-horizontal wh pos (dirs2 dir))
                                      (move-vertical wh row [col] (dirs2 dir)))]
                    [nwh (if move? (addv pos (dirs dir)) pos)])) [warehouse start] moves)
        (first)
        (coordinates \[ rng1 rng2))))

(def part-2 (move-all-2 data))
;; => 1468005
