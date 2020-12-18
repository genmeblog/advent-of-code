(ns advent-of-code-2020.day18
  (:require [common :refer [read-data]]
            [clojure.string :as str]))

(defn prepare [line] (str/escape line {\space ""}))

(def data (map prepare (read-data 2020 18)))

(def V (reduce #(assoc %1 %2 (read-string (str %2))) {\+ + \* *} "123456789"))

(defn ->tree
  ([expr] (->tree [] expr))
  ([curr [token & expr]]
   (cond
     (not token) curr
     (= \) token) [curr expr]
     (= \( token) (let [[v nr] (->tree expr)]
                    (->tree (conj curr v) nr))
     :else (->tree (conj curr token) expr))))

(defn calculate
  ([tree] (calculate (partial +) tree))
  ([curr [token & r]]
   (cond
     (not token) curr
     (sequential? token) (calculate (curr (calculate token)) r)
     (#{\+ \*} token) (calculate (partial (V token) curr) r)
     :else (calculate (curr (V token)) r))))

(def part-1 (reduce + (map (comp calculate ->tree) data)))
;; => 800602729153

(defn fix-order
  [tree]
  (->> tree
       (reduce #(conj %1 (if (sequential? %2) (fix-order %2) %2)) [])
       (partition-by #{\*})
       (mapcat (fn [s] (if (> (count s) 2) [s] s)))))

(def part-2 (reduce + (map (comp calculate fix-order ->tree) data)))
;; => 92173009047076
