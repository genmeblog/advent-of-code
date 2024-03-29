(ns advent-of-code-2021.day07
  (:require [common :refer [read-single-line parse]]
            [clojure.string :as str]
            [fastmath.stats :as stats]
            [fastmath.core :as m]
            [clojure2d.core :as c2d]
            [fastmath.kernel :as k]
            [clojure2d.extra.utils :as utils]
            [clojure2d.color :as c]
            [fastmath.optimization :as opt]))

(def data (parse (str/split (read-single-line 2021 7) #",")))

(defn dist [a b] (int (m/abs (- a b))))
(defn fuel-burn [data cost m] (reduce #(+ %1 (cost %2 m)) 0 data))

(def part-1 (fuel-burn data dist (stats/median data)))
;; => 336040

(defn cost [d] (/ (* d (inc d)) 2))

;; ah no, brute force...
(defn fuel-burn-valid
  [data]
  (->> data
       (reduce max)
       (inc)
       (range)
       (map (partial fuel-burn data (comp cost dist)))
       (reduce min)))

(def part-2 (fuel-burn-valid data))
;; => 94813675

;; vis

(c2d/with-canvas [c (c2d/canvas 600 750)]
  (c2d/set-background c 0x003366)
  (let [[mn mx] (stats/extent data)
        pos-m (m/norm (stats/median data) mn mx 50 550)
        density (k/kernel-density :gaussian data 5)]
    
    (c2d/set-color c 0xffe592 150)
    (doseq [[id submarine] (map-indexed vector data)
            :let [yy (m/norm id 0 1000 50 550)
                  xx (m/norm submarine mn mx 50 550)]]
      (c2d/line c xx yy pos-m yy))
    
    (c2d/set-color c (c/brighten 0x0066cc) 100)
    (doseq [id (range (- mn 100) (+ mx 100))
            :let [len (* 25000 (density id))
                  xx (m/norm id mn mx 50 550)]]
      (c2d/line c xx (- 650 len) xx (+ 650 len)))

    (c2d/set-color c 0xffe592 150)
    (c2d/rect c (dec pos-m) 50 3 500))

  (utils/show-image c)
  #_(c2d/save c "images/advent_of_code_2021/day07.jpg"))

;; opt

(defn dist [a b] (int (m/abs (- a b))))
(defn fuel-burn [data cost-fn m] (reduce #(+ %1 (cost-fn %2 m)) 0 data))
(defn cost [d] (/ (* d (inc d)) 2))
(def target1 (comp (partial fuel-burn data dist) int))
(def target2 (comp (partial fuel-burn data (comp cost dist)) int))
(defn optimizer [target] 
  (->> {:bounds [[0 2000]] :N 100}
       (opt/scan-and-minimize :cmaes target)
       (second)
       (int)))

(optimizer target1)
;; => 336040
(optimizer target2)
;; => 94813675

;; --- also valid
(target1 (fastmath.stats/median data))
;; => 336040
(target2 (fastmath.stats/mean data))
;; => 94813675

