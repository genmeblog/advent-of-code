(ns advent-of-code-2018.day01
  (:require [clojure.java.io :as io]))

(set! *unchecked-math* :warn-on-boxed)
(set! *warn-on-reflection* true)

(def frequencies-diff
  (delay (map read-string (-> (io/resource "day01.txt")
                              (io/reader)
                              (line-seq)))))

(def freq-dup
  (delay (let [freqs (reductions + 0 (cycle @frequencies-diff))]
           (reduce (fn [visited freq]
                     (if (visited freq)
                       (reduced freq)
                       (conj visited freq))) #{} freqs))))

(time {:final-frequency (reduce + @frequencies-diff)
       :duplicated-frequency @freq-dup})
;; => {:final-frequency 582, :duplicated-frequency 488}
