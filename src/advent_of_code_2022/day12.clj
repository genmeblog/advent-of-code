(ns advent-of-code-2022.day12
  (:require [common :refer [read-data bfs->path-count]]))

;; (int \S) ;; => 83
;; (int \E) ;; => 69

(defn parse [line] (mapv int line))

(defn around-fn [data [x y :as pos]]
  (filter #(<= (- (get-in data % Integer/MAX_VALUE)
                  (get-in data pos)) 1) [[x (inc y)] [(inc x) y] [(dec x) y] [x (dec y)]]))

(defn process
  [data]
  (reduce (fn [m pos]
            (let [v (get-in data pos)]
              (cond
                (= v 83) (assoc m :start pos)
                (= v 69) (assoc m :end pos)
                (and (#{97 83} v)
                     ((set (map #(get-in data %) (around-fn data pos))) 98)) (update m :a conj pos)
                :else m))) {} (for [col (range (count (first data)))
                                    row (range (count data))]
                                [row col])))

(defn parse-data
  [data]
  (let [data (mapv parse data)
        {:keys [start end] :as processed} (process data)]
    (assoc processed :data (-> data (assoc-in start 97) (assoc-in end 122)))))

(def data (parse-data (read-data 2022 12)))

(defn get-path-count
  [{:keys [data start end]}]
  (bfs->path-count (partial around-fn data) start end))

(def part-1 (get-path-count data))
;; => 534

(defn search-minimum-path
  [data]
  (->> (:a data)
       (pmap #(get-path-count (assoc data :start %)))
       (reduce min)))

(defonce part-2 (search-minimum-path data))
;; => 525
