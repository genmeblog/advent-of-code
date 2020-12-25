(ns advent-of-code-2020.day25)

(defn steps [^long n] (iterate (fn [^long i] (mod (* i n) 20201227)) n))

(defn loop-number
  ^long [^long target]
  (->> (steps 7)
       (keep-indexed (fn [id ^long v] (when (== v target) id)))
       (first)))

(def part-1 (nth (steps 8184785) (loop-number 5293040)))
;; => 4126980
