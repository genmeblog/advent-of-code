(ns advent-of-code-2020.day15)

(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)

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
