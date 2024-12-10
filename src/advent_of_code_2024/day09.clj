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
   (if (neg? fid) ;; the end of files
     buff
     (let [[fpos fsize] (files fid)] ;; get current file posistion and size
       (if (<= fpos (or gpos ##Inf)) ;; all gaps filled already, just calculate checksum for the rest
         (recur files gaps (dec fid) (+ buff (checksum fpos fsize fid)))
         (cond
           ;; gap and file sizes are the same
           (== gsize fsize) (recur files gaps (dec fid) 
                                   (+ buff (checksum gpos fsize fid)))
           ;; gap size bigger than file, fill it, make gap smaller, move to next file
           (> gsize fsize) (recur files (conj gaps [(+ gpos fsize) (- gsize fsize)]) (dec fid)
                                  (+ buff (checksum gpos fsize fid)))
           ;; gaps size too small, fill it, move to next gap, update file size
           :else (recur (assoc files fid [fpos (- fsize gsize)]) gaps fid
                        (+ buff (checksum gpos gsize fid)))))))))

(def part-1 (move-to-gap data))
;; => 6242766523059

;; linear search for required gap size (up to file position, move only backward not forward)
(defn find-in-gaps
  ([gaps total fsize fpos] (find-in-gaps gaps total fsize fpos 0))
  ([gaps total fsize fpos id]
   (when (< id total) ;; check if inside gap list
     (let [[gpos gsize] (gaps id)]
       (when (< gpos fpos) ;; gap is before file
         (if (>= gsize fsize) ;; file fits in a gap, return gap position and size
           [id gpos gsize]
           ;; move to next gap
           (recur gaps total fsize fpos (inc id))))))))

;; Part 2
(defn fit-in-gap
  ([{:keys [files gaps total]}] (fit-in-gap files gaps total (dec total) 0))
  ([files gaps total fid buff]
   (if (neg? fid) ;; the end of files
     buff
     (let [[fpos fsize] (files fid)
           gapdata (find-in-gaps gaps total fsize fpos)] ;; find valid gap
       (if gapdata ;; gap is found
         (let [[gapid gpos gsize] gapdata
               ;; update gap size
               ngaps (assoc gaps gapid [(+ gpos fsize) (- gsize fsize)])] 
           (recur files ngaps total (dec fid) ;; calculate checksum for a file in found gap
                  (+ buff (checksum gpos fsize fid))))
         (recur files gaps total (dec fid) ;; no gap, do not move file, calculate checksum
                (+ buff (checksum fpos fsize fid))))))))

(def part-2 (fit-in-gap data))
;; => 6272188244509
