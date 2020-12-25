(ns advent-of-code-2020.day25)

(def ^:const ^long d1 8184785)
(def ^:const ^long d2 5293040)

(def ^:const ^long m 20201227)

(defn steps
  [^long n]
  (iterate (fn [^long i]
             (mod (* i n) m)) n))

(defn loop-number
  ^long [^long target]
  (->> (steps 7)
       (map-indexed vector)
       (drop-while (comp (fn [^long v] (not= v target)) second))
       (ffirst)))

(def part-1 (nth (steps d1) (loop-number d2)))
;; => 4126980
