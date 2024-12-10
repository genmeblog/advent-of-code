(ns advent-of-code-2024.day09
  (:require [common :refer [read-single-line]]))

(set! *unchecked-math* :warn-on-boxed)

;; Returns a map containing:
;; * :files - [starting-block file-size]
;; * :gaps - [starting-block gap-size]

(defn produce-lists
  [data]
  (let [[files gaps] (->> data
                          (map (comp parse-long str))
                          (partition-all 2)
                          (reduce (fn [[files gaps ^long block] [^long fsize ^long gsize]]
                                    (let [end1 (+ block fsize)
                                          gsize (or gsize 0)]
                                      [(conj files [block fsize])
                                       (conj gaps [end1 gsize])
                                       (+ end1 gsize)])) [[] [] 0]))]
    {:files files :gaps gaps}))

(def data (produce-lists (read-single-line 2024 9)))

;; sum of range [start,(start + size)] = (size)*(size+2*start-1)/2
(defn checksum ^long [^long buff ^long start ^long size ^long fid]
  (+ buff (quot (* fid size (+ size (* 2 start) -1)) 2)))

;; Part 1
(defn move-to-gap
  ([{:keys [files gaps]}] (move-to-gap files gaps (dec (count files)) 0))
  ([files [[^long gpos ^long gsize] & gaps] ^long fid ^long buff]
   (if (neg? fid) ;; the end of files
     buff
     (let [[^long fpos ^long fsize] (files fid)] ;; get current file posistion and size
       (if (<= fpos (or gpos Long/MAX_VALUE)) ;; all gaps filled already, just calculate checksum for the rest
         (recur files gaps (dec fid) (checksum buff fpos fsize fid))
         (cond
           ;; gap and file sizes are the same
           (== gsize fsize) (recur files gaps (dec fid) (checksum buff gpos fsize fid))
           ;; gap size bigger than file, fill it, make gap smaller, move to next file
           (> gsize fsize) (recur files (conj gaps [(+ gpos fsize) (- gsize fsize)]) (dec fid)
                                  (checksum buff gpos fsize fid))
           ;; gaps size too small, fill it, move to next gap, update file size
           :else (recur (assoc files fid [fpos (- fsize gsize)]) gaps fid
                        (checksum buff gpos gsize fid))))))))

(def part-1 (move-to-gap data))
;; => 6242766523059

;; linear search for required gap size (up to file position, move only backward not forward)
(defn find-in-gaps
  ([gaps fsize fpos] (find-in-gaps gaps fsize fpos 0))
  ([gaps ^long fsize ^long fpos ^long id]
   (when (< id (count gaps)) ;; check if inside gap list
     (let [[^long gpos ^long gsize] (gaps id)]
       (when (< gpos fpos) ;; gap is before file
         (if (>= gsize fsize) ;; file fits in a gap, return gap position and size
           [id gpos gsize]
           ;; move to next gap
           (recur gaps fsize fpos (inc id))))))))

;; Part 2
(defn fit-in-gap
  ([{:keys [files gaps]}] (fit-in-gap files gaps (dec (count files)) 0))
  ([files gaps ^long fid ^long buff]
   (if (neg? fid) ;; the end of files
     buff
     (let [[^long fpos ^long fsize] (files fid)
           gapdata (find-in-gaps gaps fsize fpos)] ;; find a valid gap
       (if gapdata ;; gap is found
         (let [[^long gapid ^long gpos ^long gsize] gapdata
               ;; update gap size
               ngaps (assoc gaps gapid [(+ gpos fsize) (- gsize fsize)])]
           ;; calculate checksum for a file in found gap
           (recur files ngaps (dec fid) (checksum buff gpos fsize fid)))
         ;; no gap, do not move file, calculate checksum
         (recur files gaps (dec fid) (checksum buff fpos fsize fid)))))))

(def part-2 (fit-in-gap data))
;; => 6272188244509
