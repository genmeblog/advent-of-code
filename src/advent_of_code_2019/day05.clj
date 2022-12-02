(ns advent-of-code-2019.day05
  (:require [common :refer [read-single-line]]
            [clojure.string :as s]))

(def memory (mapv read-string
                (-> (read-single-line 2019 5)
                    (s/split #","))))

(defn make-machine
  [memory input]
  {:input input
   :output []
   :pc 0
   :memory memory})

(defn step [m c] (update m :pc + c))
(defmacro R [memory a mode] `(if ~mode (~memory ~a) ~a))

(defn in
  [m [d]]
  (let [v (first (:input m))]
    (-> (update m :input rest)
        (update :memory assoc d v)
        (step 2))))

(defn out
  [{:keys [memory] :as all} mode1 [d]]
  (-> (update all :output conj (R memory d mode1))
      (step 2)))

(defn binary
  [{:keys [memory] :as all} f [mode1 mode2] [d1 d2 a]]
  (-> (update all :memory assoc a (f (R memory d1 mode1)
                                     (R memory d2 mode2)))
      (step 4)))

(defn jump
  [{:keys [memory] :as all} pred [mode1 mode2] [par1 par2]]
  (if (pred (R memory par1 mode1))
    (assoc all :pc (R memory par2 mode2))
    (step all 3)))

(defn cmp-fun [pred v1 v2] (if (pred v1 v2) 1 0))

(defn parse-opcode
  [^long in]
  (let [opcode (mod in 100)
        m1 (mod (quot in 100) 10)
        m2 (mod (quot in 1000) 10)]
    [opcode (zero? m1) (zero? m2)]))

(defn executor
  [{:keys [^long pc memory] :as m}]
  (let [[opcode mode1 mode2] (parse-opcode (memory pc))
        rst (subvec memory (inc pc))]
    (case opcode
      1 (binary m + [mode1 mode2] rst)
      2 (binary m * [mode1 mode2] rst)
      3 (in m rst)
      4 (out m mode1 rst)
      5 (jump m (complement zero?) [mode1 mode2] rst)
      6 (jump m zero? [mode1 mode2] rst)
      7 (binary m (partial cmp-fun <) [mode1 mode2] rst)
      8 (binary m (partial cmp-fun =) [mode1 mode2] rst)
      99 (assoc m :pc -1))))

(defn run-machine
  [machine]
  (->> machine
       (iterate executor)
       (drop-while (comp (complement neg?) :pc))
       first))

(defn run-part
  ([memory] (run-part memory [0]))
  ([memory input]
   (-> (make-machine memory input)
       (run-machine)
       :output
       last)))

(def part-1 (run-part memory [1]))
;; => 9219874

(def part-2 (run-part memory [5]))
;; => 5893654
