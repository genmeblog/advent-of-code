(ns advent-of-code-2019.day03
  (:require [clojure.java.io :as io]
            [clojure.string :as s]
            [fastmath.core :as m]))

;; data structure is a vector with
;;
;; len - length of the segment
;; a - first end position
;; b - second end position
;; c - common coordinate
;; o - orientation `:v` or `:h`
;; sid - segment id
;; id - wire id
;;
;; intersection structure is a vector with
;;
;; d - distance
;; x,y - cross coordinates
;; sid1, sid2 - segment id of the intersection

(defn intersection
  [[_ a1 b1 c1 o1 sid1 id1]
   [_ a2 b2 c2 o2 sid2 id2]]
  (when (and (not= o1 o2) ;; not aligned
             (< id1 id2) ;; two different wires, ordered
             (or (<= a1 c2 b1)
                 (<= b1 c2 a1)) 
             (or (<= a2 c1 b2)
                 (<= b2 c1 a2)))
    (let [d (int (+ (m/abs c1) (m/abs c2)))]
      (when-not (zero? d)
        (if (= o1 :v) [d c1 c2 sid1 sid2] [d c2 c1 sid1 sid2])))))

(defn process-segment
  [[_ _ b c _ sid id] new]
  (let [dir (first new)
        f (if (#{\L \D} dir) -1 1) ;; direction of coords change
        o (if (#{\R \L} dir) :h :v) ;; orientation
        len (read-string (apply str (rest new)))]
    [len c (+ c (* f len)) b o (inc sid) id]))

(defn process-wire
  [[id wire]]
  (rest (reductions process-segment [0 0 0 0 nil 0 id] (s/split wire #","))))

(def segments (->> (-> (io/resource "day03.txt")
                       (io/reader)
                       (line-seq))
                   (map-indexed vector)
                   (mapcat process-wire)))

(defn intersections
  [segments]
  (for [s1 segments
        s2 segments
        :let [[d _ _ :as cr] (intersection s1 s2)]
        :when (and d (pos? d))]
    cr))

(def wires-intersections (intersections segments))

(defn minimum-distance
  [wires-intersections]
  (->> wires-intersections
       (map first)
       (apply min)))

(def part-1 (minimum-distance wires-intersections))

(defn before-intersection? [sid seg] (>= sid (nth seg 5)))

(defn find-segment-sum
  [gs sid id c1 c2]
  (let [ss (take-while (partial before-intersection? sid) (gs id))
        [_ a b _ o] (last ss)
        init (if (= :h o)
               (if (< a b) (- c1 a) (- a c1))
               (if (< a b) (- c2 a) (- a c2)))] ;; find subsegment length from endpoint to intersection
    (reduce + init (map first (butlast ss))))) ;; sum the rest

(defn process-intersection
  [gs [_ c1 c2 sid1 sid2]]
  (+ (find-segment-sum gs sid1 0 c1 c2)
     (find-segment-sum gs sid2 1 c1 c2)))

(defn minimum-lengths
  [wires-intersections grouped-segments]
  (apply min (map (partial process-intersection grouped-segments) wires-intersections)))

(def part-2 (minimum-lengths wires-intersections (group-by last segments)))
