(ns advent-of-code-2024.day07
  (:require [common :refer [read-data get-numbers]]))

(def data (map get-numbers (read-data 2024 07)))

(defmacro ->gen-combinations [nm init]
  `(def ~nm (memoize (fn [^long length#]
                     (if (== 1 length#)
                       [~@(map vector init)]
                       (mapcat (fn [~'l] [~@(for [op init] `(conj ~'l ~op))]) (~nm (dec length#))))))))

(->gen-combinations combinations2 [* +])

;;;;; inline combinations and cut wrong branches

(defn calculate
  ^long [^long t ^long v r combination]
  (->> (map vector r combination)
       (reduce (fn [^long v1 [^long v2 op]]
                 (let [res (long (op v1 v2))]
                   (if (> res t) (reduced 0) res))) v)))

(defn check-line
  [comb-fn [^long t ^long f & r]]
  (if (->> (comb-fn (count r))
           (some (fn [combination] (== t (calculate t f r combination)))))
    t 0))

(defn answer [comb-fn data]
  (->> data
       (pmap (partial check-line comb-fn))
       (reduce +)))

(def part-1 (answer combinations2 data))
;; => 850435817339

(defn || ^long [^long a ^long b]
  (cond
    (< b 10) (+ (* a 10) b)
    (< b 100) (+ (* a 100) b)
    (< b 1000) (+ (* a 1000) b)))

(->gen-combinations combinations3 [* + ||])

(defonce part-2 (time (answer combinations3 data)))
;; => 104824810233437
