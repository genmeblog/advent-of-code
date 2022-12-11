(ns advent-of-code-2022.day10
  (:require [common :refer [read-data split-line]]))

(defn process-data
  [data]
  (->> data
       (reduce (fn [ticks op]
                 (let [nticks (conj ticks 0)]
                   (if-let [arg (-> op split-line second)]
                     (conj nticks (parse-long arg))
                     nticks))) [1])
       (reductions +)))

(def data (process-data (read-data 2022 10)))

(defn signal-strength
  [data]
  (->> (range 20 221 40)
       (map #(* % (nth data (dec %))))
       (reduce +)))

(def part-1 (signal-strength data))
;; => 13520

(defn generate-signal
  [data lit nlit]
  (map-indexed (fn [id sprite-pos]
                 (if (#{(dec sprite-pos) sprite-pos (inc sprite-pos)} (mod id 40))
                   lit nlit)) data))

(defn part-2
  [data]
  (->> (generate-signal data "■" "□")
       (partition 40)
       (map #(apply str %))
       (run! println)))

(part-2 data)

;; PGPHBEAB

;; ■■■□□□■■□□■■■□□■□□■□■■■□□■■■■□□■■□□■■■□□
;; ■□□■□■□□■□■□□■□■□□■□■□□■□■□□□□■□□■□■□□■□
;; ■□□■□■□□□□■□□■□■■■■□■■■□□■■■□□■□□■□■■■□□
;; ■■■□□■□■■□■■■□□■□□■□■□□■□■□□□□■■■■□■□□■□
;; ■□□□□■□□■□■□□□□■□□■□■□□■□■□□□□■□□■□■□□■□
;; ■□□□□□■■■□■□□□□■□□■□■■■□□■■■■□■□□■□■■■□□
