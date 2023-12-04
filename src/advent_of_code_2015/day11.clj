(ns advent-of-code-2015.day11)

(def next-letter (into {} (partitionv 2 1 "abcdefghjkmnpqrstuvwxyz!")))

(defn invalid-password? [s]
  (or (not (re-find #"abc|bcd|cde|def|efg|fgh|pqr|qrs|rst|stu|tuv|uvw|vwx|wxy|xyz" s))
      (<= (-> (re-seq #"([a-z])\1" s) distinct count) 1)))

(defn increment [[ch & r]]
  (if-not (seq r)
    (str (next-letter ch))
    (let [c (increment r)]
      (if (= \! (first c))
        (str (next-letter ch) "a" (subs c 1))
        (str ch c)))))

(defn next-password [s]
  (->> (iterate increment s)
       (drop-while invalid-password?)
       (first)))

(def part-1 (next-password "hxbxwxba"))
;; => "hxbxxyzz"

(def part-2 (next-password (increment part-1)))
;; => "hxcaabcc"
