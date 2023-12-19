(ns advent-of-code-2023.day19
  (:require [common :refer [read-data-as-blocks]]
            [clojure.string :as str]))

(defn parse-part [part]
  (read-string (str/escape part {\= \space})))

(defn parse-rule [rule]
  (let [[_ rule-name conds] (re-find #"^(\w+)\{(.+)\}" rule)
        conds (->> (str/split conds #",")
                   (map #(map read-string (re-seq #"[a-zA-Z]+|[<>]|[0-9]+" %))))]
    [(symbol rule-name) conds]))

(defn parse [[rules parts]]
  {:parts (map parse-part parts)
   :conds (assoc (into {} (map parse-rule rules)) 'A 'A 'R 'R)})

(def data (parse (read-data-as-blocks 2023 19)))

(defn accepted-part?
  ([conds part] (accepted-part? conds part 'in))
  ([conds part rule]
   (if (#{'R 'A} rule)
     (= 'A rule)
     (loop [[[cat cnd v nxt] & r] (conds rule)]
       (if-let [cndf ({'> > '< <} cnd)]
         (if-not (cndf (part cat) v)
           (recur r)
           (accepted-part? conds part nxt))
         (accepted-part? conds part cat))))))

(defn find-accepted [{:keys [conds parts]}]
  (->> (filter (partial accepted-part? conds) parts)
       (mapcat vals)
       (reduce +)))

(def part-1 (find-accepted data))
;; => 353553

(defn block-size [part]
  (->> (vals part)
       (map (fn [[a b]] (inc (abs (- a b)))))
       (reduce *)))

(defn split-at-v [[a b] cnd v]
  (if (= '< cnd)
    [[a (dec v)] [v b]]
    [[(inc v) b] [a v]]))

(defn accepted-ranges
  ([conds part] (accepted-ranges conds part (conds 'in)))
  ([conds part rule]
   (if (#{'R 'A} rule)
     (if (= 'A rule) (block-size part) 0)
     (let [[[cat cnd v nxt] & r] rule]
       (if-not cnd
         (recur conds part (conds cat))
         (let [[t f] (split-at-v (part cat) cnd v)]
           (+ (accepted-ranges conds (assoc part cat t) (conds nxt))
              (accepted-ranges conds (assoc part cat f) r))))))))

(defn find-accepted-ranges [data]
  (accepted-ranges (:conds data) (zipmap '(x m a s) (repeat [1 4000]))))

(def part-2 (find-accepted-ranges data))
;; => 124615747767410
