(ns advent-of-code-2021.day12
  (:require [common :refer [read-data parse]]))

(def conjs (fnil conj #{}))

(defn ->graph
  [data]
  (-> (->> data
           (map (partial parse #"(\w+)-(\w+)"))
           (reduce (fn [m [a b]]
                     (-> (update m a conjs b)
                         (update b conjs a))) {}))
      (update-vals #(disj % 'start))))

(def data (->graph (read-data 2021 12)))

(def capital-or-end? (comp (partial re-matches #"[A-Z]+|end") name))
(def not-finished? (comp #(not= 'end %) first :path))

(defn find-paths
  ([data part1?] (find-paths data part1? [{:visited #{'start} :path '(start)}]))
  ([data part1? paths]
   (if (some not-finished? paths)
     (->> paths
          (mapcat (fn [{:keys [visited path double?] :as m}]
                    (if (not-finished? m)
                      (->> (data (first path))
                           (remove (if (or part1? double?) visited #{}))
                           (map (fn [cave]
                                  {:visited (if (capital-or-end? cave) visited (conj visited cave))
                                   :double? (or double? (visited cave))
                                   :path (conj path cave)})))
                      [m])))
          (recur data part1?))
     (count paths))))

(def part-1 (time (find-paths data true)))
;; => 5958

(def part-2 (time (find-paths data false)))
;; => 150426
