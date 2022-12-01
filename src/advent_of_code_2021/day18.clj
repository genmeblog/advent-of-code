(ns advent-of-code-2021.day18
  (:require [common :refer [read-data]]))

(def data (map read-string (read-data 2021 18)))

(defn find-paths
  ([tree] (find-paths tree [] {:buff []}))
  ([tree current buff]
   (if (vector? tree)
     (if (and (not (:pos buff))
              (= 4 (count current)))
       (-> buff
           (update :buff conj current)
           (assoc :pos (count (:buff buff))))
       (->> buff
            (find-paths (first tree) (conj current 0))
            (recur (second tree) (conj current 1))))
     (update buff :buff conj current))))

(defn explode
  [tree]
  (let [{:keys [pos buff]} (find-paths tree)]
    (if pos
      (let [[l r] (get-in tree (get buff pos))]
        {:result :done
         :tree (as-> tree t
                 (if (pos? pos) (update-in t (get buff (dec pos)) + l) t)
                 (if (< pos (dec (count buff))) (update-in t (get buff (inc pos)) + r) t)
                 (assoc-in t (get buff pos) 0))})
      {:tree tree})))

(defn split-path
  ([tree] (split-path tree []))
  ([tree current]
   (if (vector? tree)
     (or (split-path (first tree) (conj current 0))
         (split-path (second tree) (conj current 1)))
     (when (> tree 9) current))))

(defn split
  [tree]
  (if-let [path (split-path tree)]
    {:result :done :tree (update-in tree path #(let [v (int (/ % 2))]
                                                 [v (- % v)]))}
    {:tree tree}))

(defn reduce-tree
  [tree]
  (let [{:keys [result tree]} (explode tree)]
    (if result
      (recur tree)
      (let [{:keys [result tree]} (split tree)]
        (if result
          (recur tree)
          tree)))))

(defn add
  [tree1 tree2]
  (reduce-tree [tree1 tree2]))

(def reduce-numbers (partial reduce add))

(defn magnitude
  [tree]
  (if (vector? tree)
    (+ (* 3 (magnitude (first tree)))
       (* 2 (magnitude (second tree))))
    tree))

(def part-1 (magnitude (reduce-numbers data)))

(def part-2 (->> (for [n1 data
                     n2 data
                     :when (not= n1 n2)]
                 (magnitude (add n1 n2)))
               (reduce max)))
