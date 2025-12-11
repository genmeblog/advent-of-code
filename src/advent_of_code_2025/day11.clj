(ns advent-of-code-2025.day11
  (:require [common :refer [read-data]]))

(defn parse-line [line] (let [[f & r] (map keyword (re-seq #"\w+" line))] [f r]))
(defn parse [data] (into {} (map parse-line data)))

(def data (parse (read-data 2025 11)))

(defn update-map
  [m from to]
  (-> m
      (update from + (m :none))
      (assoc :none 0)
      (update :both + (m to))
      (assoc to 0)))

(defn merge-maps
  [m1 m2 node]
  (let [nm (merge-with + m1 m2)]
    (case node
      :dac (update-map nm :dac :fft)
      :fft (update-map nm :fft :dac)
      nm)))

(defn count-paths
  ([data node field] (get-in (count-paths data node) [node field]))
  ([data node]
   (let [targets (data node)]
     (if (= [:out] targets)
       (assoc data node {:none 1 :both 0 :fft 0 :dac 0})
       (let [[ndata m] (reduce (fn [[data m] target]
                                 (let [v (data target)]
                                   (if (map? v)
                                     [data (merge-maps m v node)]
                                     (let [ndata (count-paths data target)]
                                       [ndata (merge-maps m (ndata target) node)])))) [data {}] targets)]
         (assoc ndata node m))))))

(def part-1 (count-paths data :you :none))
;; => 497

(def part-2 (count-paths data :svr :both))
;; => 358564784931864
