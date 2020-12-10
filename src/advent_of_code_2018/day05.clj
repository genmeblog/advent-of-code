(ns advent-of-code-2018.day05
  (:require [clojure.java.io :as io]
            [clojure.string :as s]))

(set! *unchecked-math* :warn-on-boxed)
(set! *warn-on-reflection* true)

(def polymer (->> (slurp (io/resource "day05.txt"))
                  (s/trim)
                  (mapv int)
                  (delay)))

(defn react [poly]
  (reduce #(if (and (seq %1) (== 32 (Math/abs (- ^int (first %1) ^int %2))))
             (rest %1)
             (cons %2 %1)) nil poly))

(def removed-unit-reactions
  (delay (pmap (fn [^long upper ^long lower]
                 (let [npolymer (remove #(or (== ^int % lower)
                                             (== ^int % upper)) @polymer)]
                   (count (react npolymer)))) (range 97 123) (range 65 91))))

(time {:reaction-units (count (react @polymer))
       :shortest (apply min @removed-unit-reactions)})
;; => {:reaction-units 11540, :shortest 6918}
