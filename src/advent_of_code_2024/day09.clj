(ns advent-of-code-2024.day09
  (:require [common :refer [read-single-line]]))

;; Returns [files, gaps]:
;; * files [starting-block file-size file-id]
;; * gaps [starting-block gap-size]

(defn produce-lists
  [data]
  (let [[files gaps] (->> data
                          (map (comp parse-long str))
                          (partition-all 2)
                          (reduce (fn [[files gaps block] [fsize gsize]]
                                    (let [end1 (+ block fsize)
                                          gsize (or gsize 0)]
                                      [(conj files [block fsize])
                                       (conj gaps [end1 gsize])
                                       (+ end1 gsize)])) [[] [] 0]))]
    {:files files :gaps gaps :total (count files)}))

(def data (produce-lists (read-single-line 2024 9)))

;; sum of range [start,(start + size)] = (size)*(size+2*start-1)/2
(defn checksum [start size fid]
  (quot (* fid size (+ size (* 2 start) -1)) 2))

;; Part 1
(defn move-to-gap
  ([{:keys [files gaps total]}] (move-to-gap files gaps (dec total) 0))
  ([files [[gpos gsize] & gaps] fid buff]
   (if (neg? fid)
     buff
     (let [[fpos fsize] (files fid)]
       (if (<= fpos (or gpos ##Inf))
         (recur files gaps (dec fid) (+ buff (checksum fpos fsize fid)))
         (cond
           (== gsize fsize) (recur files gaps (dec fid)
                                   (+ buff (checksum gpos fsize fid)))
           (> gsize fsize) (recur files (conj gaps [(+ gpos fsize) (- gsize fsize)]) (dec fid)
                                  (+ buff (checksum gpos fsize fid)))
           :else (recur (assoc files fid [fpos (- fsize gsize)]) gaps fid
                        (+ buff (checksum gpos gsize fid)))))))))

(def part-1 (move-to-gap data))
;; => 6242766523059

;; linear search for required gap size (up to file position, move only backward not forward)
(defn find-in-gaps
  ([gaps total fsize fpos] (find-in-gaps gaps total fsize fpos 0))
  ([gaps total fsize fpos id]
   (when (< id total)
     (let [[gpos gsize] (gaps id)]
       (when (< gpos fpos)
         (if (>= gsize fsize)
           [id gpos gsize]
           (recur gaps total fsize fpos (inc id))))))))

;; Part 2
(defn fit-in-gap
  ([{:keys [files gaps total]}] (fit-in-gap files gaps total (dec total) 0))
  ([files gaps total fid buff]
   (if (neg? fid)
     buff
     (let [[fpos fsize] (files fid)
           gapdata (find-in-gaps gaps total fsize fpos)]
       (if gapdata
         (let [[gapid gpos gsize] gapdata
               ngaps (assoc gaps gapid [(+ gpos fsize) (- gsize fsize)])] 
           (recur files ngaps total (dec fid)
                  (+ buff (checksum gpos fsize fid))))
         (recur files gaps total (dec fid)
                (+ buff (checksum fpos fsize fid))))))))

(def part-2 (fit-in-gap data))
;; => 6272188244509
