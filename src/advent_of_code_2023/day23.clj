(ns advent-of-code-2023.day23
  (:require [common :refer [read-data ->priority-queue priority set-priority]]
            [clojure2d.core :as c2d]
            [clojure2d.extra.utils :as utils]
            [clojure.string :as str]))

(def data1 (vec (read-data "examples" 2023 23)))
(def data (vec (read-data 2023 23)))

(def data2 ["#.##"
          "#..#"
          "##.#"
          "##.#"])

(defn add [[a b] [c d]] [(+ a c) (+ b d)])
(defn inside? [size [a b]] (and (>= a 0) (>= b 0) (<= a size) (<= b size)))

(defn neighbours [data size visited pos]
  (->> (condp = (get-in data pos)
         \^ [(add pos [-1 0])]
         \v [(add pos [1 0])]
         \> [(add pos [0 1])]
         \< [(add pos [0 -1])]
         (->> [[0 1] [1 0] [0 -1] [-1 0]]
              (map (partial add pos))
              (remove (comp #{\#} (partial get-in data)))
              (filter (partial inside? size)))         )
       (remove visited)))

(defn find-path
  ([data] (let [cnt (dec (count data))]
            (find-path data cnt [0 1] [cnt (dec cnt)])))
  ([data size start end]
   (loop [[[curr visited] & r :as paths] (list [start #{start}])
          finished []]
     (cond
       (not (seq paths)) finished
       (= curr end) (recur r (conj finished visited))
       :else (recur (->> (neighbours data size visited curr)
                         (map (fn [n] [n (conj visited n)]))
                         (reduce conj r))
                    finished)))))

(defn no-slopes [data] (mapv #(str/replace % #"[<>^v]" ".") data))

(reduce max (map (comp dec count) (find-path (no-slopes data1))))
;; => 2414

(defn find-next
  ([data size start end] (find-next data size #{start} start end))
  ([data size visited start end]
   (let [[v :as ns] (neighbours data size visited start)]
     (if (not= 1 (count ns))
       [(- (count visited)) (if (seq ns) start end) ns]
       (recur data size (conj visited v) v end)))))

(find-next data2 3 [0 1] [3 2])

(find-next (no-slopes data1) (dec (count data1)) [0 1] [10 20])

(defn find-longest-path
  ([data] (let [cnt (dec (count data))]
            (find-longest-path data cnt [0 1] [cnt (dec cnt)])))
  ([data size start end]
   (println "end=" end)
   (let [[cost start ns] (find-next data size start end)]
     (find-longest-path data size (-> (->priority-queue)
                                      (set-priority [start ns] cost)) {start cost} end 0)))
  ([data size Q visited end i]
   (let [[pos ns :as state] (peek Q)
         curr-cost (priority Q state)]
     (if (or (= i 120) (not (seq Q)))
       [visited (peek Q) i curr-cost]
       (let [[nQ nvisited] (reduce (fn [[q v :as qv] n]
                                     (let [[cost npos ns] (find-next data size #{pos n} n end)
                                           ncost (+ cost curr-cost)]
                                       (if (v npos)
                                         qv
                                         [(set-priority q [npos ns] ncost)
                                          (assoc v npos ncost)])))
                                   [Q visited] ns)]
         (recur data size (pop nQ) nvisited end (inc i)))))))

(find-longest-path (no-slopes data1))

;; vis

(let [data data1
      size 6
      hsize (/ size 2)
      cnt (count data)]
  (c2d/with-canvas [c (c2d/canvas (* size cnt) (* size cnt))]
    (c2d/set-background c 230 230 220)
    (c2d/set-color c 10 15 20)
    (doseq [row (range cnt)
            col (range cnt)
            :let [xx (* col size)
                  yy (* row size)]]
      (condp = (get-in data [row col])
        \# (c2d/rect c xx yy size size)
        \> (c2d/triangle c xx yy xx (+ yy size) (+ xx size) (+ yy hsize))
        \< (c2d/triangle c (+ xx size) yy (+ xx size) (+ yy size) xx (+ yy hsize))
        \v (c2d/triangle c xx yy (+ xx size) yy (+ xx hsize) (+ yy size))
        \^ (c2d/triangle c xx (+ yy size) (+ xx size) (+ yy size) (+ xx hsize) yy)
        nil))
    (c2d/set-color c :red 150)
    (doseq [[row col] (last (sort-by count (find-path data)))
            :let [xx (+ (* col size) hsize)
                  yy (+ (* row size) hsize)]]
      (c2d/crect c xx yy size size))
    (c2d/set-color c :green 150)
    #_(doseq [[row col] (last (sort-by count (find-path (no-slopes data))))
              :let [xx (+ (* col size) hsize)
                    yy (+ (* row size) hsize)]]
        (c2d/crect c xx yy size size))
    (utils/show-image c)))
