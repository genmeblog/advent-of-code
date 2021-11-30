(ns advent-of-code-2016.day04
  (:require [common :refer [read-data]]))

(defn parser
  [in]
  (let [[_ room id checksum] (re-find #"([a-z\-]+)\-(\d+)\[([a-z]+)\]" in)]
    [room (read-string id) checksum]))

(def data (->> (read-data 2016 4)
               (map parser)))

(defn valid-room?
  [[room _ checksum]]
  (->> room
       (remove #{\-})
       (group-by identity)
       (sort-by (fn [[k v]] [(- (count v)) k]))
       (map first)
       (filter (set checksum))
       (= (seq checksum))))

(def part-1 (reduce + (map second (filter valid-room? data))))
;; => 158835

(def alphabet "abcdefghijklmnopqrstuvwxyz")

(defn decrypter
  [id]
  (assoc (zipmap alphabet (take 26 (drop id (cycle alphabet)))) \- \space))

(def part-2 (->> data
                 (filter valid-room?)
                 (map (fn [[room id]]
                        [id (apply str (map (decrypter id) room))]))
                 (filter #(= "northpole object storage" (second %)))
                 (ffirst)))
;; => 993
