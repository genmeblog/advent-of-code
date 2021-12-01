(ns advent-of-code-2016.day05
  (:require [fastmath.core :as m])
  (:import [java.security MessageDigest]))

(set! *unchecked-math* :warn-on-boxed)
(set! *warn-on-reflection* true)
(m/use-primitive-operators)

(def data "ugkcyxxp")

(def ^MessageDigest md5 (MessageDigest/getInstance "MD5"))

(defn five0?
  [dgs]
  (< (bit-or (<< (bit-and 0xff (aget ^bytes dgs 0)) 16)
             (<< (bit-and 0xff (aget ^bytes dgs 1)) 8)
             (bit-and 0xff (aget ^bytes dgs 2))) 16))

(defn find-md5
  [s]
  (filter identity (map #(let [^bytes dgs (.digest md5 (.getBytes (str s %)))]
                           (when (five0? dgs) (aget dgs 2))) (range))))

(defn hex
  [in]
  (->> in
       (map (partial format "%x"))
       (apply str)))

(defn password
  [id]
  (->> id
       (find-md5)
       (take 8)
       (hex)))

(defonce part-1 (password data))
;; => "d4cd2ee1"

(defn find-md5-position
  [s ^long pref]
  (some #(let [^bytes dgs (.digest md5 (.getBytes (str s %)))]
           (when (and (five0? dgs)
                      (== (aget dgs 2) pref))
             [(aget dgs 2) (bit-and 0xf (>> (aget dgs 3) 4))])) (range)))

(defn position-password
  [in]
  (hex (reduce #(let [[pos v] (find-md5-position in %2)]
                  (assoc %1 (int pos) v)) [0 0 0 0 0 0 0 0] (range 8))))

(defonce part-2 (position-password data))
;; => "f2c730e5"
