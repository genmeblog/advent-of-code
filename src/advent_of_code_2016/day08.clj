(ns advent-of-code-2016.day08
  (:require [common :refer [read-data]]
            [clojure.string :as str]
            [clojure2d.core :as c2d]
            [clojure2d.extra.utils :as utils]))

(defprotocol PScreen
  (rotate-row [s row cnt])
  (rotate-col [s col cnt])
  (rect [s w h]))

(def regex-rotate #".*=(\d+) by (\d+)")
(def regex-rect #".* (\d+)x(\d+)")
(defn parse [re line] (map read-string (rest (re-find re line))))

(defn parser
  [line]
  (condp #(str/starts-with? %2 %1) line
    "rotate row" [rotate-row (parse regex-rotate line)]
    "rotate col" [rotate-col (parse regex-rotate line)]
    "rect" [rect (parse regex-rect line)]))

(def data (map parser (read-data 2016 8)))

(defrecord Screen [w h screen]
  PScreen
  (rotate-row [_ row cnt] (->Screen w h (set (for [[x y :as pos] screen]
                                               (if (= y row) [(mod (+ x cnt) w) y] pos)))))
  (rotate-col [_ col cnt] (->Screen w h (set (for [[x y :as pos] screen]
                                               (if (= x col) [x (mod (+ y cnt) h)] pos)))))
  (rect [_ ww hh] (->Screen w h (reduce conj screen (for [x (range ww)
                                                          y (range hh)]
                                                      [x y])))))

(defn screen [w h] (->Screen w h #{}))

(def result (:screen (reduce (fn [s [op [x y]]] (op s x y)) (screen 50 6) data)))

(def part-1 (count result))
;; => 116

;;

(-> (c2d/with-canvas [c (c2d/canvas 600 100)]
      (c2d/set-background c :gray)
      (c2d/set-color c [50 50 50])
      (c2d/translate c 50 20)
      (doseq [[x y] result]
        (c2d/rect c (* x 10) (* y 10) 10 10))
      (utils/show-image c)
      #_(c2d/save c "images/advent_of_code_2016/day08.jpg")))
