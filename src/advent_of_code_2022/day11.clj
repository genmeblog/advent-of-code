(ns advent-of-code-2022.day11
  (:require [common :refer [read-data-as-blocks split-line]]))

(def get-numbers (partial re-seq #"\d+"))
(def get-number (comp parse-long first get-numbers))

(defn maybe-long [v] (when-not (= v "old") (parse-long v)))

(defn parse
  [[monkey items op divisor t f]]
  (let [[a o b] (drop 3 (split-line op))
        fun (resolve (symbol o))
        arga (maybe-long a)
        argb (maybe-long b)]
    {:id (get-number monkey)
     :items (map parse-long (get-numbers items))
     :divisor (get-number divisor)
     :t (get-number t)
     :f (get-number f)
     :op (fn [arg] (fun (if arga arga arg) (if argb argb arg)))
     :inspected 0}))

(def data (mapv parse (read-data-as-blocks 2022 11)))

(defn inspect
  [inspect-f monkeys id]
  (let [{:keys [items divisor t f op]} (monkeys id)]
    (-> (reduce (fn [m item]
                  (let [wl (inspect-f op item)
                        target (if (zero? (mod wl divisor)) t f)]
                    (update-in m [target :items] conj wl))) monkeys items)
        (update-in [id :items] empty)
        (update-in [id :inspected] + (count items)))))

(defn round
  [inspect-f monkeys]
  (reduce (partial inspect inspect-f) monkeys (map :id monkeys)))

(defn most-inspected
  [data inspect-f cnt]
  (->> (nth (iterate (partial round inspect-f) data) cnt)
       (map :inspected)
       (sort >)
       (take 2)
       (reduce *)))

(defn most-inspected1 [data]
  (most-inspected data (fn [op item] (quot (op item) 3)) 20))

(def part-1 (most-inspected1 data))
;; => 110888

(defn most-inspected2 [data]
  (most-inspected data (let [divisors (reduce * (map :divisor data))] ;; all are primes!
                         (fn [op item] (mod (op item) divisors))) 10000))

(def part-2 (most-inspected2 data))
;; => 25590400731
