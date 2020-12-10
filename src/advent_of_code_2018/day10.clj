(ns advent-of-code-2018.day10
  (:require [clojure.java.io :as io]
            [clojure2d.core :refer :all]
            [fastmath.core :as m]))

(set! *unchecked-math* :warn-on-boxed)
(set! *warn-on-reflection* true)

(def ^:const ^int mini Integer/MIN_VALUE)
(def ^:const ^int maxi Integer/MAX_VALUE)

(def coords (mapv #(let [[x y vx vy] (map read-string (rest (re-find #"position=<([-\s]?\d+),\s+([-\s]?\d+)>\svelocity=<([-\s]?\d+),\s+([-\s]?\d+)>" %)))]
                     [[x y] [vx vy]])
                  (->> "day10.txt"
                       (io/resource)
                       (io/reader)
                       (line-seq))))

(defn min-max [[^long minx ^long miny ^long maxx ^long maxy] [^long x ^long y]]
  [(min minx x) (min miny y) (max maxx x) (max maxy y)])

(defn max-coord [^long mx [^long x ^long y]] (max mx x y (Math/abs x) (Math/abs y)))

(defn next-move
  ([^long scale [[^long x ^long y] [^long vx ^long vy :as v]]]
   [[(+ x (* scale vx)) (+ y (* scale vy))] v])
  ([v] (next-move 1 v)))

(defn animate
  [canvas _ _ [crds ^long time ^long curr-area :as state]]
  (let [[^long minx ^long miny ^long maxx ^long maxy :as varea] (reduce min-max [maxi maxi mini mini] (mapv first crds))
        ex (- maxx minx)
        hx (* ex 0.5)
        area (* ex (- maxy miny))]
    (if (< area curr-area) ; draw only when current area is smaller
      (do
        (-> canvas
            (set-background :black 50)
            (set-color :red))
        (doseq [[[x y]] crds
                :let [sx (m/norm x (- minx 10) (+ maxx 10) 0 (width canvas))
                      sy (m/norm y (- miny hx) (+ maxy hx) 0 (height canvas))]]
          (ellipse canvas sx sy 10 10))
        (-> canvas
            (set-color :white)
            (text (str time) 20 20))
        [(mapv next-move crds) (inc time) area])
      state)))

(def c (canvas 800 800 :highest))

(defn calc-extent [f]
  (reduce max-coord Integer/MIN_VALUE (mapv f coords)))

(def initial-time
  (let [extent (calc-extent first)
        vextent (calc-extent second)]
    (long (* 0.93 (long (/ ^long extent ^long vextent))))))

(show-window {:canvas c
              :draw-state [(map (partial next-move initial-time) coords) initial-time maxi]
              :draw-fn animate
              :window-name "d10"})

(defmethod key-pressed ["d10" \space] [_ _]
  (save c "images/day10.jpg"))
