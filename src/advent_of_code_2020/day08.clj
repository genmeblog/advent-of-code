(ns advent-of-code-2020.day08
  (:require [common :refer [read-data]]
            [clojure.string :as str]))

(defn parse
  [line]
  (let [[opcode data] (str/split line #" ")]
    [opcode (read-string data)]))

(def data (mapv parse (read-data 2020 8)))

(defn execute
  ([code] (execute code 0 0 #{}))
  ([code acc pc visited]
   (cond
     (visited pc) {:loop acc}
     (= pc (count code)) {:terminated acc}
     (> pc (count code)) {:bug acc}
     :else (let [[opcode arg] (code pc)
                 nvisited (conj visited pc)]
             (condp = opcode
               "acc" (recur code (+ acc arg) (inc pc) nvisited)
               "jmp" (recur code acc (+ pc arg) nvisited)
               (recur code acc (inc pc) nvisited))))))

(def part-1 (execute data))
;; => {:loop 1548}

(def opcode-exchange {"jmp" "nop" "nop" "jmp" "acc" "acc"})

(defn simulate
  [code]
  (->> (range (count code))
       (map #(execute (update-in code [% 0] opcode-exchange)))
       (filter :terminated)
       (first)))

(def part-2 (simulate data))
;; => {:terminated 1375}
