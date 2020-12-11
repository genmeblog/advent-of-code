(ns advent-of-code-2015.day04
  (:require [fastmath.core :as m])
  (:import [java.security MessageDigest]))

(def ^MessageDigest md5 (MessageDigest/getInstance "MD5"))

(defn find-md5
  [pred s]
  (some #(let [^bytes dgs (.digest md5 (.getBytes (str s %)))]
           (when (pred (m/bit-or (m/<< (m/bit-and 0xff (aget dgs 0)) 16)
                                 (m/<< (m/bit-and 0xff (aget dgs 1)) 8)
                                 (m/bit-and 0xff (aget dgs 2)))) %)) (rest (range))))

(defn five0? [^long v] (< v 16))
(defn six0? [^long v] (zero? v))

(defonce part-1 (find-md5 five0? "iwrupvqb"))
;; => 346386

(defonce part-2 (find-md5 six0? "iwrupvqb"))
;; => 9958218
