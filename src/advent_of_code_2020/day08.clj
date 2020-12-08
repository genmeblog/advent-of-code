(ns advent-of-code-2020.day08
  (:require [advent-of-code-2020.common :refer [read-data]]
            [clojure.string :as str]))

(defn parse
  [line]
  (let [[opcode data] (str/split line #" ")]
    [opcode (read-string data)]))

(def data (mapv parse (read-data 8)))

(defn execute
  ([code] (execute code {:acc 0 :pc 0 :visited #{}}))
  ([code {:keys [acc pc visited] :as state}]
   (cond
     (visited pc) [:loop acc]
     (= pc (count code)) [:terminated acc]
     (> pc (count code)) [:bug acc]
     :else (let [[opcode arg] (code pc)
                 nvisited (conj visited pc)]
             (condp = opcode
               "acc" (recur code (assoc state :acc (+ acc arg) :pc (inc pc) :visited nvisited))
               "jmp" (recur code (assoc state :pc (+ pc arg) :visited nvisited))
               (recur code (assoc state :pc (inc pc) :visited nvisited)))))))

(def part-1 (execute data))
;; => [:loop 1548]

(def opcode-exchange {"jmp" "nop"
                      "nop" "jmp"
                      "acc" "acc"})

(defn simulate
  [code]
  (->> (count code)
       (range)
       (map (fn [id] (execute (update-in code [id 0] opcode-exchange))))
       (drop-while (complement (comp #{:terminated} first)))
       (first)))

(def part-2 (simulate data))
;; => [:terminated 1375]
