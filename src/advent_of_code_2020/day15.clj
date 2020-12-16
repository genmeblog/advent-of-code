(ns advent-of-code-2020.day15
  (:require [fastmath.core :as m]))

(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)
(m/use-primitive-operators)

(def input [12,1,16,3,11,0])

(deftype Day15 [^int spoken ^int turn ^ints arr])

(defn data
  [input ^long size]
  (let [arr (int-array (+ 50 size))]
    (doseq [[^int id ^int v] (map-indexed vector (butlast input))]
      (aset ^ints arr v (inc id)))
    (->Day15 (last input) (count input) arr)))

(defn game-step
  [^Day15 data]
  (let [visited (aget ^ints (.arr data) (.spoken data))]
    (aset ^ints (.arr data) (.spoken data) (.turn data))
    (if (zero? visited)
      (Day15. 0 (inc (.turn data)) (.arr data))
      (Day15. (- (.turn data) visited) (inc (.turn data)) (.arr data)))))

(defn game
  [input ^long n]
  (let [^Day15 res (-> game-step
                       (iterate (data input n))
                       (nth (- n (count input))))]
    (.spoken res)))

(def part-1 (game input 2020))
;; => 1696

(def part-2 (time (game input 30000000)))
;; => 37385



;;;;;;;;;;;;;
;;
;; IntIntMap4a
;; http://java-performance.info/implementing-world-fastest-java-int-to-int-hash-map/
;;

(defrecord IntIntMap [^ints data free-key? ^int free-value
                      ^double fill-factor ^long size ^long threshold
                      ^long mask ^long mask2])

(defn- array-size
  ^long [^long expected ^double f]
  (max 2 (m/round-up-pow2 (m/ceil (/ expected f)))))

(defn ->IntIntMap
  ^IntIntMap [^long size ^double fill-factor]
  (let [capacity (array-size size fill-factor)]
    (println "Capacity=" (* 2 capacity))
    (IntIntMap. (int-array (* 2 capacity))
                false 0
                fill-factor 0 (long (* capacity fill-factor))
                (dec capacity)
                (dec (* 2 capacity)))))

(defn phi-mix
  ^long [^long x]
  (let [h (* x 0x9E3779B9)]
    (bit-xor h (>> h 16))))

(defn get-val
  ^long [^IntIntMap m ^long ky]
  (if (zero? ky)
    (if (.free-key? m) (.free-value m) -1)
    (loop [ptr (<< (bit-and (phi-mix ky) (.mask m)) 1)]
      (let [k (aget ^ints (.data m) ptr)]
        (cond
          (zero? k) -1
          (== k ky) (aget ^ints (.data m) (inc ptr))
          :else (recur (bit-and (+ ptr 2) (.mask2 m))))))))

(declare set-val)

(defn rehash
  [^IntIntMap m]
  (let [old-capacity (alength ^ints (.data m))
        new-capacity (* 2 old-capacity)
        threshold (* old-capacity (.fill_factor m))
        mask (dec old-capacity)
        mask2 (dec new-capacity)
        old-data (.data m)
        new-data (int-array new-capacity)]
    (println old-capacity " - > " new-capacity)
    (reduce (fn [^IntIntMap m ^long i]
              (let [k (aget ^ints old-data i)]
                (if (pos? k)
                  (set-val m k (aget ^ints old-data (inc i)))
                  m)))
            (IntIntMap. new-data (.free-key? m) (.free-value m)
                        (.fill-factor m) (if (.free-key? m) 1 0) threshold
                        mask mask2)
            (range 0 old-capacity 2))))

(defn set-val
  ^IntIntMap [^IntIntMap m ^long ky ^long vl]
  (if (== ky 0)
    (IntIntMap. (.data m) true vl
                (.fill-factor m) (if-not (.free-key? m) (inc (.size m)) (.size m)) (.threshold m)
                (.mask m) (.mask2 m))
    (loop [ptr (<< (bit-and (phi-mix ky) (.mask m)) 1)]
      (let [k (aget ^ints (.data m) ptr)]
        (cond
          (zero? k) (do
                      (aset ^ints (.data m) ptr ky)
                      (aset ^ints (.data m) (inc ptr) vl)
                      (if (> (.size m) (.threshold m))
                        (rehash m)
                        (IntIntMap. (.data m) (.free-key? m) (.free-value m)
                                    (.fill-factor m) (inc (.size m)) (.threshold m)
                                    (.mask m) (.mask2 m))))
          (== k ky) (do
                      (aset ^ints (.data m) (inc ptr) vl)
                      m)
          :else (recur (bit-and (+ ptr 2) (.mask2 m))))))))

(defn data-2
  [input ^long n]
  (->Day15 (last input) (count input)
           (reduce (fn [m [^int id ^int v]]
                     (set-val m v (inc id))) (->IntIntMap (* 0.075 n) 0.5) (map-indexed vector (butlast input)))))

(defn game-step-2
  [^Day15 data]
  (let [visited (get-val (.arr data) (.spoken data))
        new-arr (set-val (.arr data) (.spoken data) (.turn data))]
    (if (neg? visited)
      (Day15. 0 (inc (.turn data)) new-arr)
      (Day15. (- (.turn data) visited) (inc (.turn data)) new-arr))))

(defn game-2
  [input ^long n]
  (let [^Day15 res (-> game-step-2
                       (iterate (data-2 input n))
                       (nth (- n (count input))))]
    (.spoken res)))

(def part-1-2 (game-2 input 2020))
;; => 1696

(def part-2-2 (time (game-2 input 30000000)))
;; => 37385
