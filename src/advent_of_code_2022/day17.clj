(ns advent-of-code-2022.day17
  (:require [common :refer [read-single-line]]
            [clojure.set :as set]
            [clojure.string :as str])
  (:import [java.util TreeSet SortedSet]))

(defn left [[a b c d e f g :as v]] (if a v [b c d e f g nil]))
(defn right [[a b c d e f g :as v]] (if g v [nil a b c d e f]))
(defn pdown [tuple] (when tuple (map dec tuple)))
(defn down [v] (map pdown v))

(def parse {\< left \> right})

(def data (cycle (map parse (read-single-line 2022 17))))

(def data2 (cycle (map parse ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>")))

(defn shape1 [pos] [nil nil [pos] [pos] [pos] [pos] nil])
(defn shape2 [pos] [nil nil [(inc pos)] [pos (inc pos) (+ pos 2)] [(inc pos)] nil nil])
(defn shape3 [pos] [nil nil [pos] [pos] [pos (inc pos) (+ pos 2)] nil nil])
(defn shape4 [pos] [nil nil [pos (inc pos) (+ pos 2) (+ pos 3)]  nil nil nil])
(defn shape5 [pos] [nil nil [pos (inc pos)] [pos (inc pos)] nil nil nil])
(def shapes (cycle [shape1 shape2 shape3 shape4 shape5]))

(defn make-chamber [] (mapv (fn [_] (TreeSet. [0])) (range 7)))

(defn overlap? [ch sh] (some identity (map (fn [^SortedSet col shcol]
                                          (some identity (map #(.contains col %) shcol))) ch sh)))
(defn get-max [ch] (reduce (fn [b ^SortedSet col] (max b (.last col))) 0 ch))
(defn get-min [ch] (reduce (fn [b ^SortedSet col] (min b (.last col))) Integer/MAX_VALUE ch))
(defn add-shape [ch shape] (mapv (fn [^SortedSet col sh]
                                (when sh (.addAll col sh))
                                col) ch shape))
(defn maybe-cut [ch] (let [mn (get-min ch)]
                    [mn (mapv (fn [^SortedSet ch] (.tailSet ch mn)) ch)]))


(defn drop-shape
  [chamber dirs shape]
  (loop [ch chamber
         sh (shape (+ 4 (get-max chamber)))
         [dir & rst] dirs]
    (let [nsh (dir sh)
          nsh (if (overlap? ch nsh) sh nsh)
          nsh-down (down nsh)]
      (if (overlap? ch nsh-down)
        [(add-shape ch nsh) rst]
        (recur ch nsh-down rst)))))

(let [[ch nd] (drop-shape (make-chamber) data2 shape1)
      [ch nd] (drop-shape ch nd shape2)
      [ch nd] (drop-shape ch nd shape3)
      [ch nd] (drop-shape ch nd shape4)
      [ch nd] (drop-shape ch nd shape5)
      [ch nd] (drop-shape ch nd shape1)
      [ch nd] (drop-shape ch nd shape2)
      [ch nd] (drop-shape ch nd shape3)
      [ch nd] (drop-shape ch nd shape4)
      [ch nd] (drop-shape ch nd shape5)
      [ch nd] (drop-shape ch nd shape2)
      [ch nd] (drop-shape ch nd shape3)
      [ch nd] (drop-shape ch nd shape4)
      [ch nd] (drop-shape ch nd shape5)
      [ch nd] (drop-shape ch nd shape1)
      [ch nd] (drop-shape ch nd shape2)
      [ch nd] (drop-shape ch nd shape3)
      [ch nd] (drop-shape ch nd shape4)
      [ch nd] (drop-shape ch nd shape5)]
  (maybe-cut ch))

(defn drop-shapes
  [data n]
  (loop [ch (make-chamber)
         dirs data
         [sh & rst] shapes
         blocks 0]
    (if (>= blocks n)
      [(get-max ch) ch]
      (do
        (when (= 10001 (get-max ch)) (println blocks)) ;;
        (when (= (+ 10001 42352) (get-max ch)) (println blocks)) ;; lengths are repeated every 5271
        (let [[nch ndir] (drop-shape ch dirs sh)]
          (recur nch ndir rst (inc blocks)))))))

(def part-1 (first (drop-shapes data 2022)))
;; => 3159

(defn find-pattern
  [data]
  (let [diffs (map (fn [col] (map (fn [[a b]] (- b a)) (partition 2 1 col))) (second (drop-shapes data 100000)))]
    (nth diffs 5)))

(defn big
  [cycle-len cycle-diff start]
  (+ (* cycle-diff (quot (- 1000000000000 start) cycle-len))
     (first (drop-shapes data2 (+ start (mod (- 1000000000000 start) cycle-len))))))

(first (drop-shapes data (+ (* 4 42352) 6387)))
;; => 209007

;; => 142668


(- 78493 73223)

(- 25908 21250)

(- 7294 4647)

(big 35 53 10000)

(big 3364 5271 6387)

(- 33427 6387)
;; => 27040


(find-pattern data)





(reduce + [17 32 1 1 1 1])
(reduce + [11 1 5 5 1 4 3 5 1 5 1 1 1 1 1 1 1 1 1 1 1 1 ])
(reduce + [5 1 1 1 1 1 1 1 2 1 1 1 1 4 1 1 5 1 3 1 1 1 1 1 3 3 1 1 1 2 2 1 1 ])
(reduce + [16 6 1 1 5 16 2 1 1 3 1])

(let [ch [(TreeSet. [2287])
          (TreeSet. [2287])
          (TreeSet. [2292 2295])
          (TreeSet. [2292 2295])
          (TreeSet. [2287, 2288, 2289, 2290, 2291, 2292, 2294, 2295, 2296, 2297])
          (TreeSet. [2292, 2293, 2294, 2295])
          (TreeSet. [2294])]]
  (println (shape4 (+ 4 (get-max ch))))
  (drop-shape ch [left left right left left left left right left left left left right right left left left left right right right left right left right right left left left left right left left right left left right right right left left left left right right right right left left left] shape4))





;;

(defn shape-1 [bottom] (set [[2 bottom] [3 bottom] [4 bottom] [5 bottom]]))
(defn shape-2 [bottom] (set [[3 bottom] [2 (inc bottom)] [3 (inc bottom)] [4 (inc bottom)] [3 (+ bottom 2)]]))
(defn shape-3 [bottom] (set [[2 bottom] [3 bottom] [4 bottom] [4 (inc bottom)] [4 (+ bottom 2)]]))
(defn shape-4 [bottom] (set [[2 bottom] [2 (inc bottom)] [2 (+ bottom 2)] [2 (+ bottom 3)]]))
(defn shape-5 [bottom] (set [[2 bottom] [3 bottom] [2 (inc bottom)] [3 (inc bottom)]]))
(def shapes (cycle [shape-1 shape-2 shape-3 shape-4 shape-5]))

(def chamber (set (map #(vector % 0) (range 7))))

(defn move-lr [shape dir] (set (map (fn [[x y]] [(dir x) y]) shape)))
(defn inside? [shape] (every? #(<= 0 % 6) (map first shape)))
(defn move-down [shape] (set (map (fn [[x y]] [x (dec y)]) shape)))

(defn steps [data]
  (reductions (fn [[ch sh [next-sh & rest-sh :as shs] top rocks] dir]
                (let [nsh (move-lr sh dir)
                      nsh (if (and (inside? nsh)
                                   (not (seq (set/intersection ch nsh)))) nsh sh)
                      nsh2 (move-down nsh)]
                  (if (seq (set/intersection ch nsh2))
                    (let [ntop (max top (reduce max (map second nsh)))]
                      [(set/union ch nsh) (next-sh (+ ntop 4)) rest-sh ntop (inc rocks)])
                    [ch nsh2 shs top rocks])))
              [chamber ((first shapes) 4) (rest shapes) 0 0]
              data))

(defn height [n] (nth (first (drop-while #(< (last %) n)
                                      (steps (cycle (map parse ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>"))))) 3))

(height 10000)
;; => 1568

(->> (steps (cycle (map parse ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>")))
     (take 10000)
     (map #(nth % 3))
     #_     (partition 2 1)
     #_(map (fn [[a b]] (- b a))))
;; => 


(def s1 (apply str '(1 3 2 1 2 1 3 2 2 0 1 3 2 0 2 1 3 3 4 0 1 2 3 0 1 1 3 2 2 0 0 2 3 4 0 1 2 1 2 0 1 2 1 2 0 1 3 2 0 0 1 3 3 4 0 1 2 3 0 1 1 3 2 2 0 0 2 3 4 0 1 2 1 2 0 1 2 1 2 0 1 3 2 0 0 1 3 3 4 0 1 2 3 0 1 1 3 2 2 0 0 2 3 4 0 1 2 1 2 0 1 2 1 2 0 1 3 2 0 0 1 3 3 4 0 1 2 3 0 1 1 3 2 2 0 0 2 3 4 0 1 2 1 2 0 1 2 1 2 0 1 3 2 0 0 1 3 3 4 0 1 2 3 0 1 1 3 2 2 0 0 2 3 4 0 1 2 1 2 0 1 2 1 2 0 1 3 2 0 0 1 3 3 4 0 1 2 3 0 1 1 3 2 2 0 0 2 3 4 0 1 2 1 2 0 1 2 1 2 0 1 3 2 0 0 1 3 3 4 0 1 2 3 0 1 1 3 2 2 0 0 2 3 4 0 1 2 1 2 0 1 2 1 2 0 1 3 2 0 0 1 3 3 4 0 1 2 3 0 1 1 3 2 2 0 0 2 3 4 0 1 2 1 2 0 1 2 1 2 0 1 3 2 0 0 1 3 3 4 0 1 2 3 0 1 1 3 2 2 0 0 2 3 4 0 1 2 1 2 0 1 2 1 2 0 1 3 2 0 0 1 3 3 4 0 1 2 3 0 1 1 3 2 2 0 0 2 3 4 0 1 2 1 2 0 1 2 1 2 0 1 3 2 0 0 1 3 3 4 0 1 2 3 0 1 1 3 2 2 0 0 2 3 4 0 1 2 1 2 0 1 2 1 2 0 1 3 2 0 0 1 3 3 4 0 1 2 3 0 1 1 3 2 2 0 0 2 3 4 0 1 2 1 2 0 1 2 1 2 0 1 3 2 0 0 1 3 3 4 0 1 2 3 0 1 1 3 2 2 0 0 2 3 4 0 1 2 1 2 0 1 2 1 2 0 1 3 2 0 0 1 3 3 4 0 1 2 3 0 1 1 3 2 2 0 0 2 3 4 0 1 2 1 2 0 1 2 1 2 0 1 3 2 0 0 1 3 3 4 0 1 2 3 0 1 1 3 2 2 0 0 2 3 4 0 1 2 1 2 0 1 2 1 2 0 1 3 2 0 0 1 3 3 4 0 1 2 3 0 1 1 3 2 2 0 0 2 3 4 0 1 2 1 2 0 1 2 1 2 0 1 3 2 0 0 1 3 3 4 0 1 2 3 0 1 1 3 2 2 0 0 2 3 4 0 1 2 1 2 0 1 2 1 2 0 1 3 2 0 0 1 3 3 4 0 1 2 3 0 1 1 3 2 2 0 0 2 3 4 0 1 2 1 2 0 1 2 1 2 0 1 3 2 0 0 1 3 3 4 0 1 2 3 0 1 1 3 2 2 0 0 2 3 4 0 1 2 1 2 0 1 2 1 2 0 1 3 2 0 0 1 3 3 4 0 1 2 3 0 1 1 3 2 2 0 0 2 3 4 0 1 2 1 2 0 1 2 1 2 0 1 3 2 0 0 1 3 3 4 0 1 2 3 0 1 1 3 2 2 0 0 2 3 4 0 1 2 1 2 0 1 2 1 2 0 1 3 2 0 0 1 3 3 4 0 1 2 3 0 1 1 3 2 2 0 0 2 3 4 0 1 2 1 2 0 1 2 1 2 0 1 3 2 0 0 1 3 3 4 0 1 2 3 0 1 1 3 2 2 0 0 2 3 4 0 1 2 1 2 0 1 2 1 2 0 1 3 2 0 0 1 3 3 4 0 1 2 3 0 1 1 3 2 2 0 0 2 3 4 0 1 2 1 2 0 1 2 1 2 0 1 3 2 0 0 1 3 3 4 0 1 2 3 0 1 1 3 2 2 0 0 2 3 4 0 1 2 1 2 0 1 2 1 2 0 1 3 2 0 0 1 3 3 4 0 1 2 3 0 1 1 3 2 2 0 0 2 3 4 0 1 2 1 2 0 1 2 1 2 0 1 3 2 0 0 1 3 3 4 0 1 2 3 0 1 1 3 2 2 0 0 2 3 4 0 1 2 1 2 0 1 2 1 2 0 1 3 2 0 0 1 3 3 4 0 1 2 3 0 1 1 3 2 2 0 0 2 3 4 0 1 2 1 2 0 1 2 1 2 0 1 3 2 0 0 1 3 3 4)))

(def s2 (apply str '(1 3 3 2 0 1 3 2 1 2 1 2 1 3 0 0 3 0 2 2 1 3 2 1 0 0 3 3 0 2 1 3 3 2 0 1 3 0 0 1 1 3 2 2 2 0 2 2 1 2 1 3 2 2 0 1 3 3 4 2 1 3 0 2 0 1 2 3 4 0 1 3 3 2 0 1 2 3 2 0 1 2 2 4 2 0 3 0 3 0 1 3 0 0 0 1 3 2 2 0 1 3 2 0 0 1 3 0 2 2 1 3 3 0 0 1 2 1 2 2 0 3 2 0 0 1 3 2 2 0 1 3 3 4 0 1 0 3 0 0 1 3 2 0 1 1 2 1 2 0 0 2 3 0 2 1 3 0 4 0 0 2 1 2 2 0 0 3 2 2 0 0 2 2 0 0 2 3 0 0 1 3 2 2 0 1 3 3 0 0 1 3 3 0 0 1 2 2 4 0 1 2 2 2 0 1 3 2 2 0 1 3 0 3 2 1 3 3 0 2 0 0 3 0 0 1 3 3 2 0 1 2 3 0 1 0 3 2 2 2 1 3 3 2 2 1 3 2 2 2 0 0 3 4 0 1 3 2 0 2 1 3 2 2 0 0 2 3 0 2 1 3 2 2 0 0 2 3 0 0 0 3 3 2 0 1 3 3 0 0 1 3 3 2 0 1 2 3 4 0 1 3 3 2 2 1 2 1 0 2 0 3 0 0 2 0 3 2 2 0 1 2 3 0 2 1 3 2 4 2 1 3 3 2 2 1 3 0 1 0 1 3 2 0 0 0 3 3 4 2 1 3 2 0 0 1 3 2 2 0 1 3 3 2 0 1 0 3 1 0 0 3 2 1 2 1 2 3 0 1 1 3 3 4 0 1 3 2 2 0 1 3 3 4 0 1 3 2 0 2 0 2 3 0 2 1 3 2 1 2 1 2 3 0 2 1 2 1 0 2 1 3 3 2 0 0 3 0 1 0 1 3 3 0 2 1 1 2 1 2 1 3 3 2 2 1 3 3 0 2 1 3 3 2 0 1 3 2 2 0 1 2 1 3 0 1 3 0 0 2 0 1 2 1 0 0 3 3 2 2 1 3 0 4 0 1 0 3 4 0 1 3 3 2 2 1 3 3 2 2 1 3 3 0 0 1 3 3 0 2 1 3 0 4 0 1 0 3 2 2 1 3 0 3 0 1 3 0 4 0 1 3 2 0 0 1 2 3 2 0 1 3 2 2 0 1 3 3 0 2 1 3 3 0 0 1 2 2 4 2 1 3 2 2 2 0 2 3 0 2 1 2 1 2 2 1 3 0 3 2 1 3 2 4 2 0 2 2 0 0 0 2 2 1 0 0 3 2 0 0 0 2 2 0 0 1 3 3 2 2 1 2 3 2 2 0 2 2 2 0 1 2 1 2 0 0 2 1 2 2 1 2 1 4 0 1 3 2 0 0 0 2 3 0 0 1 2 3 0 2 1 2 1 2 2 1 2 3 2 0 1 3 2 4 0 1 3 0 2 0 0 3 3 4 0 1 3 3 2 2 1 3 2 2 0 1 3 0 0 2 1 3 0 1 0 1 2 3 2 2 1 3 2 0 0 1 3 0 2 0 1 2 3 0 0 1 3 0 4 0 0 2 2 2 0 1 3 3 4 2 1 0 3 2 2 1 3 3 0 0 1 3 3 2 2 1 3 3 0 2 1 3 2 4 0 0 2 0 3 2 0 0 3 4 0 1 3 3 0 0 0 2 3 0 0 1 3 0 0 2 1 3 3 0 0 1 3 0 0 0 1 3 3 2 0 1 3 2 0 0 1 3 2 0 1 0 3 3 0 2 1 1 2 1 0 0 2 3 0 0 1 3 2 1 2 1 3 3 2 0 1 1 3 0 0 1 3 2 2 0 1 3 3 0 2 1 3 3 4 0 1 3 2 0 2 1 3 3 0 0 1 3 3 0 0 1 3 2 2 2 1 3 3 2 2 1 3 3 0 0 1 2 3 2 0 1 2 1 4 0 1 3 3 4 0 1 3 2 2 0 1 3 2 0 2 1 2 3 0 2 1 3 2 0 2 1 3 2 2 2 1 3 3 2 2 1 3 0 4 2 1 3 2 0 0 1 3 0 3 0 1 3 3 2 2 1 3 3 2 0 1 0 3 4 0 1 3 2 2 0 1 3 3 0 0 1 3 3 0 0 1 3 2 0 2 1 3 3 0 2 1 3 2 1 0 1 3 3 4 0 1 3 2 0 0 0 3 0 2 2 0 0 3 1 0 0 3 0 2 0 1 2 3 4 2 0 0 3 4 0 1 3 2 4 0 0 0 0 2 2 0 2 0 3 2 0 0 1 3 2 1 0 0 2 0 0 2 2 2 2 1 2 1 2 0 1 2 1 2 2 1 3 0 3 0 1 2 3 2 2 1 3 3 2 0 1 2 3 0 2 1 3 2 2 0 1 3 2 0 0 0 3 0 4 2 1 3 3 0 0 0 2 3 0 2 1 3 3 4 2 1 2 3 0 2 1 2 2 0 0 1 3 3 2 0 1 3 0 2 0 1 2 1 2 0 1 3 3 0 0 1 3 3 2 2 1 2 1 0 2 1 3 3 0 0 1 3 3 0 2 1 3 0 0 1 1 3 2 2 0 1 2 2 2 2 0 0 3 2 2 1 3 3 2 0 1 2 3 2 0 1 2 2 2 2 1 2 1 2 0 1 3 3 0 0 1 3 3 4 2 1 3 3 0 0 1 2 3 0 0 1 3 2 2 0 1 3 3 2 0 1 3 3 4 0 1 3 3 0 0 1 2 2 1 1 1 3 0 4 0 0 2 3 0 0 1 3 2 2 0 1 3 3 4 0 1 3 2 0 2 1 3 2 4 0 1 2 3 2 0 1 3 2 2 0 0 2 3 2 0 1 2 3 4 0 1 3 3 4 0 1 3 0 3 0 0 2 3 2 0 1 1 2 1 2 1 2 3 2 2 1 3 2 1 2 0 3 2 4 0 1 3 2 0 0 1 2 3 0 0 0 2 2 4 0 1 3 3 2 0 1 3 3 4 0 1 3 3 2 0 1 3 3 4 0 0 0 3 0 0 1 2 2 2 2 1 2 3 2 0 1 2 3 4 0 0 0 3 2 2 1 0 3 4 0 1 2 2 2 2 1 2 1 2 0 1 3 3 0 2 1 3 3 0 2 0 0 3 2 0 1 3 3 0 0 1 3 0 4 0 1 2 3 2 2 1 2 1 2 0 1 3 2 0 0 0 2 2 2 0 1 2 3 0 0 1 3 3 0 0 0 1 2 4 0 1 3 2 0 0 1 2 3 0 0 1 3 2 1 1 1 2 2 2 2 1 3 2 0 0 1 3 2 0 2 1 3 3 0 0 1 3 3 0 0 1 3 3 4 0 1 3 3 2 0 1 3 2 2 0 1 2 3 4 2 1 3 3 4 2 1 3 3 2 0 1 3 3 2 0 1 3 0 1 0 1 3 3 2 0 1 3 0 2 0 1 2 3 0 0 1 3 3 2 0 1 3 3 0 0 1 3 2 0 0 1 3 3 0 2 1 2 3 2 0 1 2 3 0 2 1 0 0 0 2 0 3 0 3 2 1 3 0 0 0 1 3 2 4 0 1 3 3 2 2 1 3 0 2 2 0 2 3 2 0 1 3 3 4 0 1 3 3 4 2 1 3 3 2 0 0 2 3 4 0 1 2 1 2 2 1 3 3 0 0 1 3 3 0 0 1 3 3 0 2 0 3 0 0 0 0 2 3 0 0 1 2 3 0 2 0 2 3 0 2 1 2 3 0 1 0 3 2 2 2 0 0 2 2 0 0 2 3 2 2 1 3 2 0 0 1 2 3 0 0 1 2 2 2 0 1 3 3 0 0 1 3 3 4 2 1 2 2 0 2 1 3 3 0 0 1 3 3 0 2 0 3 0 0 0 1 2 3 0 2 1 3 0 0 0 1 3 2 0 2 1 3 0 2 0 1 2 2 4 2 0 0 3 2 0 1 2 1 2 0 1 3 0 4 0 1 3 3 0 0 1 2 3 0 2 1 3 3 2 2 1 3 0 0 2 1 2 1 2 0 1 3 2 2 0 0 2 2 2 2 1 3 2 1 2 1 3 2 4 0 1 3 3 2 0 1 3 3 0 0 1 1 2 1 1 1 3 2 4 0 1 0 0 4 0 1 0 3 0 0 1 3 2 1 2 1 3 0 3 2 1 3 0 4 0 1 3 2 2 0 1 2 3 4 0 1 2 2 2 0 1 3 2 2 0 1 3 0 3 2 1 3 3 0 2 0 0 3 0 0 1 3 3 2 0 1 2 3 0 1 0 3 2 2 2 1 3 3 2 2 1 3 2 2 2 0 0 3 4 0 1 3 2 0 2 1 3 2 2 0 0 2 3 0 2 1 3 2 2 0 0 2 3 0 0 0 3 3 2 0 1 3 3 0 0 1 3 3 2 0 1 2 3 4 0 1 3 3 2 2 1 2 1 0)))

(map (fn [id]
       (let [ss (subs s2 id)]
         [id (str/index-of s2 ss)])) (range (count s2)))













;; => (1 3 2 1 2 1 3 2 2 0 1 3 2 0 2 1 3 3 4 0
;;     1 2 3 0 1 1 3 2 2 0 0 2 3 4 0 1 2 1 2 0 1 2 1 2 0 1 3 2 0 0 1 3 3 4 0
;;     1 2 3 0 1 1 3 2 2 0 0 2 3 4 0 1 2 1 2 0 1 2 1 2 0 1 3 2 0 0 1 3 3 4 0
;;     1 2 3 0 1 1 3 2 2 0 0 2 3 4 0 1 2 1 2 0 1 2 1 2 0 1 3 2 0 0 1 3 3 4 0
;;     1 2 3 0 1 1 3 2 2 0 0 2 3 4 0 1 2 1 2 0 1 2 1 2 0 1 3 2 0 0 1 3 3 4 0
;;     1 2 3 0 1 1 3 2 2 0 0 2 3 4 0 1 2 1 2 0 1 2 1 2 0 1 3 2 0 0 1 3 3 4 0
;;     1 2 3 0)


(+ (reduce + [1 3 2 1 2 1 3 2 2 0 1 3 2 0 2 1 3 3 4 0])
   (* (reduce + [1 2 3 0 1 1 3 2 2 0 0 2 3 4 0 1 2 1 2 0 1 2 1 2 0 1 3 2 0 0 1 3 3 4 0])
      (quot (- 1000000000000 20) 35))
   (reduce + (take (mod (- 1000000000000  20) 35) [1 2 3 0 1 1 3 2 2 0 0 2 3 4 0 1 2 1 2 0 1 2 1 2 0 1 3 2 0 0 1 3 3 4 0])))

(- 157 (+ 15 (* 55 (quot (- 100 8) 35))))

(mod (- 100 8) 35)

(- 1514285714288 (+ (* 55 (quot (- 1000000000000 8) 36))
                    (reduce + [1 3 2 1 2 1 3 2])))
;; => 28

(mod (- 2022 8) 36)

(reduce + (take 21 [2 0 1 3 2 0 2 1 3 3 4 0 1 2 3 0 1 1 3 2 2 0 0 2 3 4 0 1 2 1 2 0 1 2 1]))


(count (read-single-line 2022 17))
;; => 10091


(nth (nth (reductions (fn [[ch sh [next-sh & rest-sh :as shs] top rocks] dir]
                        (let [nsh (move-lr sh dir)
                              nsh (if (and (inside? nsh)
                                           (not (seq (set/intersection ch nsh)))) nsh sh)
                              nsh2 (move-down nsh)]
                          (if (seq (set/intersection ch nsh2))
                            (let [ntop (max top (reduce max (map second nsh)))]
                              [(set/union ch nsh) (next-sh (+ ntop 4)) rest-sh ntop (inc rocks)])
                            [ch nsh2 shs top rocks])))
                      [chamber ((first shapes) 4) (rest shapes) 0 0]
                      data) 01) 3)
;; => 214
;; => 161
;; => 122
;; => 56

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

