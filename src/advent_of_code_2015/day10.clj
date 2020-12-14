(ns advent-of-code-2015.day10)

(defn look-and-say
  [in n]
  (-> #(apply str (mapcat (fn [seq]
                            (str (count seq) (first seq))) (partition-by identity %)))
      (iterate in)
      (nth n)
      (count)))

(def part-1 (look-and-say "1113222113" 40))
;; => 252594

(def part-2 (look-and-say "1113222113" 50))
;; => 3579328
