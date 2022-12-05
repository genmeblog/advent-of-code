(ns advent-of-code-2022.day05
  (:require [common :refer [read-data-as-blocks-no-trim]]))

(defn parse-stacks-line
  [line]
  (->> line rest (take-nth 4)))

(defn parse-instructions
  [line]
  (->> (re-seq #"\d+" line)
       (map (comp dec read-string))))

(defn parse
  [[stacks instructions]]
  {:stacks (->> (butlast stacks) ;; drop stack numbers, bottom line
                (map parse-stacks-line) ;; get crates 
                (apply map list) ;; transpose and keep in the list (stack)
                (mapv #(remove #{\space} %))) ;; remove empty spaces
   :instructions (map parse-instructions instructions)})

(def data (parse (read-data-as-blocks-no-trim 2022 5)))

(defn move-crates-step
  [reverse? stacks [cnt from to]]
  (let [[top rst] (split-at (inc cnt) (stacks from))]
    (assoc stacks
           to (reduce conj (stacks to) (if reverse? (reverse top) top))
           from rst)))

(defn move
  [{:keys [stacks instructions]} reverse?]
  (->> (reduce (partial move-crates-step reverse?) stacks instructions)
       (map first)
       (apply str)))

(def part-1 (move data false))
;; => "BZLVHBWQF"

(def part-2 (move data true))
;; => "TDGJQTZSL"

