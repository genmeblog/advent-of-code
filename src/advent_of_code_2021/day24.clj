(ns advent-of-code-2021.day24
  (:require [common :refer [read-data]]
            [clojure.string :as str]))

(defn lnth [id] #(last (nth % id)))

;; select only relevant code parts
(defn parser [data]
  (->> data 
       (map #(map read-string (str/split % #"\s")))
       (partition-by #(= 'inp (first %)))
       (rest)
       (take-nth 2)
       (map (juxt (lnth 4) (lnth 14)))))

(defn make-pair
  "Create pairs of possible min/max values for given offset"
  [off]
  (if (neg? off)
    [(inc (- off)) 9]
    [1 (- 9 off)]))

(defmacro find-model
  [typ year m]
  (let [data (parser (read-data year m))
        symbols (map #(symbol (str "w" %1)) (range 14))
        {:keys [to-for to-let]} (reduce (fn [m [id [x y]]]
                                          (if (> x 9)
                                            (update m :stack conj [id y])
                                            (let [[ref-id ref-y] (first (:stack m))
                                                  diff (+ ref-y x)]
                                              (-> (update m :stack rest)
                                                  (update :to-for conj `(make-pair ~diff) ref-id)
                                                  (update :to-let conj `(+ ~ref-id ~diff) id))))) {} (map vector symbols data))]
    `(~typ (sort (for [~@to-for
                       :let [~@to-let]]
                   (read-string (str ~@symbols)))))))


(def part-1 (find-model last 2021 24))
;; => 91297395919993

(def part-2 (find-model first 2021 24))
;; => 71131151917891

;; notes

;; inp w
;; mul x 0
;; add x z
;; mod x 26
;; div z 1
;; add x 14 ;; take this (X), if X > 9 it's grow part
;; eql x w
;; eql x 0 ;; for 'div z 26' part, we want to have x=0, this way we will have only z/26 left without any addition
;; mul y 0
;; add y 25
;; mul y x
;; add y 1
;; mul z y
;; mul y 0
;; add y w
;; add y 0 ;; take this (Y)
;; mul y x
;; add z y


;; formula for grow part grows: ( ... (w2 + Y2 + (26 * (w1 + Y1 + (26 * (w0 + Y1 + 26 * z0))))))
;; formula for shring part old-z = 26 * new-z + R, where R = some_w + some_Y
;; from equality we want to have related_w = R + X

;; data
;; => ([14 0] ;; w0
;;     [13 12] ;; w1
;;     [15 14] ;; w2 ...
;;     [13 0]
;;     [-2 3]
;;     [10 15]
;;     [13 11]
;;     [-15 12]
;;     [11 1]
;;     [-9 12]
;;     [-9 3]
;;     [-7 10]
;;     [-4 14]
;;     [-6 12])

;; w0 0 (Y)
;; w1 12
;; w2 14
;; w3 0
;; w4 = w3 + 0 - 2 (X)
;; w5 15
;; w6 11
;; w7 = w6 + 11 - 15 = w6 - 4
;; w8 1
;; w9 = w8 + 1 - 9 = w8 - 8
;; w10 = w5 + 15 - 9 = w5 + 6
;; w11 = w2 + 14 - 7 = w2 + 7
;; w12 = w1 + 12 - 4 = w1 + 8
;; w13 = w0 + 0 - 6 = w0 - 6

;; (sort (for [w0 (make-pair -6)
;;             w1 (make-pair 8)
;;             w2 (make-pair 7)
;;             w5 (make-pair 6)
;;             w8 (make-pair -8)
;;             w6 (make-pair -4)
;;             w3 (make-pair -2)
;;             :let [w13 (+ w0 -6)
;;                   w12 (+ w1 8)
;;                   w11 (+ w2 7)
;;                   w10 (+ w5 6)
;;                   w9 (+ w8 -8)
;;                   w7 (+ w6 -4)
;;                   w4 (+ w3 -2)]]
;;         (read-string (str w0 w1 w2 w3 w4 w5 w6 w7 w8 w9 w10 w11 w12 w13))))
