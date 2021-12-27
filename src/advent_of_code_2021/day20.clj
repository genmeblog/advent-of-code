(ns advent-of-code-2021.day20
  (:require [common :refer [read-data-as-blocks]]
            [clojure2d.core :as c2d]
            [clojure2d.extra.utils :as utils]))

(defn ->v [line] (mapv {\. \0 \# \1} line))
(defn parser
  [[[ruleset] grid]]
  {:ruleset (->v ruleset)
   :grid (mapv ->v grid)
   :default \0})

(def data (parser (read-data-as-blocks 2021 20)))

(defn around [x y] (for [ox [-1 0 1] oy [-1 0 1]] [(+ x ox) (+ y oy)]))
(def next-default {\0 first \1 last})

(defn process-grid
  [{:keys [ruleset grid default] :as d}]
  (let [r (range -1 (inc (count grid)))
        ngrid (for [x r y r]
                (-> (->> (around x y)
                         (map #(get-in grid % default))
                         (apply str))
                    (Integer/parseInt 2)
                    (ruleset)))]
    (-> (assoc d :default ((next-default default) ruleset))
        (assoc :grid (->> ngrid
                          (partition (count r))
                          (mapv vec))))))

(defn lit [data n] (->> n (nth (iterate process-grid data)) :grid flatten (filter #{\1}) count))

(def part-1 (lit data 2))
;; => 5597

(defonce part-2 (lit data 50))
;; => 18723

