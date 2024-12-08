(ns advent-of-code-2024.day06
  (:require [common :refer [read-data addv outside?]]
            [clojure.string :as str]))

(defn find-start [data]
  (->> data
       (map-indexed (fn [row s] (when-let [col (str/index-of s "^")] [row col])))
       (filter identity)
       (first)))

(defn parse-data [data]
  {:start (find-start data)
   :map (mapv vec data)
   :size (count data)})

(def data (parse-data (read-data 2024 06)))

(defn rotate-right [[^long x ^long y]] [y (- x)])

(defn step [m [pos dir]]
  (let [npos (addv pos dir)]
    (if (= \# (get-in m npos))
      [pos (rotate-right dir)]
      [npos dir])))

(defn traverse-map [{:keys [^long size start] :as data}]
  (let [start-pos-dir [start [-1 0]]]
    (->> (iterate (partial step (:map data)) start-pos-dir)
         (reduce (fn [buff pos-dir]
                   (cond
                     (buff pos-dir) (reduced :loop)
                     (outside? size (first pos-dir)) (reduced buff)
                     :else (conj buff pos-dir))) #{}))))

(defn visited [data]
  (set (map first (traverse-map data))))

(def part-1 (count (visited data)))
;; => 5305

(defn with-obstacles [data]
  (->> (visited data)
       (pmap (fn [pos] (traverse-map (update data :map assoc-in pos \#))))
       (filter #{:loop})
       (count)))

;; 6-8 seconds
(defonce part-2 (with-obstacles data))
;; => 2143
