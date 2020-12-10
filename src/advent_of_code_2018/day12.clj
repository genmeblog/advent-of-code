(ns advent-of-code-2018.day12
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(set! *unchecked-math* :warn-on-boxed)
(set! *warn-on-reflection* true)

(def input (delay (line-seq (io/reader (io/resource "day12.txt")))))

;; dots to spaces, for trim
(defn replace-dot [s] (str/replace s #"\." " "))

;; rules as map
(def rules (delay (into {} (map #(let [[r c] (str/split % #"\s=>\s")]
                                   [(replace-dot r) (first (replace-dot c))])) (drop 2 @input))))

;; inital state
(def init (delay (replace-dot (subs (first @input) 15))))

;; reducing function
(defn next-stage [[^long pos line]]
  (let [in (str "    " line "    ") ;; append spaces
        nl (drop-while #(= (second %) \space) ;; remove spaces keeping pot number
                       (map #(vector (+ pos (- ^long % 2)) ;; pot number
                                     (or (@rules (subs in ^long % (+ ^long % 5))) \space)) ;; apply rule
                            (range (+ 4 (count line)))))] ;; position
    [(ffirst nl) (str/trimr (str/join (mapv second nl)))])) ;; first pot id with new state

(def iterator (delay (iterate next-stage [0 @init])))

(defn pots-value [^long pos str]
  (reduce + (keep-indexed #(when (= %2 \#) (+ pos ^long %1)) str)))

(def after-5e10 (delay (reduce (fn [[^long year [^long prev-pot-id prev-str]] [^long pot-id str]]
                                 (if (= prev-str str)
                                   (let [step (- pot-id prev-pot-id)
                                         diff (- 50000000000 year)]
                                     (reduced (pots-value (+ pot-id (* diff step)) str)))
                                   [(inc year) [pot-id str]])) [0 nil] @iterator)))

(time {:after-20 (apply pots-value (first (drop 20 @iterator)))
       :after-5e10 @after-5e10})

