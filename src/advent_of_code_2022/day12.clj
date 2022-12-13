(ns advent-of-code-2022.day12
  (:require [common :refer [read-data]]))

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

(defn traverse
  [{:keys [data start end]}]
  (let [bfs (fn [[[v :as q] explored path]]
              (if (and v (not= v end))
                (recur (reduce (fn [[q explored path :as buff] pos]
                                 (if-not (explored pos)
                                   [(conj q pos)
                                    (conj explored pos)
                                    (assoc path pos v)]
                                   buff)) [(pop q) explored path] (around-fn data v)))
                path))]
    (bfs [(conj clojure.lang.PersistentQueue/EMPTY start) #{start} {}])))

(defn get-path-count
  [{:keys [start end] :as in}]
  (->> (iterate (traverse in) end)
       (take-while (complement #{start}))
       (count)))

(def part-1 (get-path-count data))
;; => 534

(defn search-minimum-path
  [data]
  (->> (:a data)
       (pmap #(get-path-count (assoc data :start %)))
       (reduce min)))

(defonce part-2 (search-minimum-path data))
;; => 525
