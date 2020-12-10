(ns advent-of-code-2015.day04
  (:require [fastmath.core :as m])
  (:import [java.security MessageDigest]))

(set! *unchecked-math* :warn-on-boxed)
(set! *warn-on-reflection* true)
(m/use-primitive-operators)

(def ^MessageDigest md5 (MessageDigest/getInstance "MD5"))

(defn find-md5
  [pred s]
  (some #(let [^bytes dgs (.digest md5 (.getBytes (str s %)))]
           (when (pred (bit-or (<< (bit-and 0xff (aget dgs 0)) 16)
                               (<< (bit-and 0xff (aget dgs 1)) 8)
                               (bit-and 0xff (aget dgs 2)))) %)) (rest (range))))

(defn five0? [^long v] (< v 16))
(defn six0? [^long v] (zero? v))

(defonce part-1 (find-md5 five0? "iwrupvqb"))
(defonce part-2 (find-md5 six0? "iwrupvqb"))
