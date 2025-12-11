(ns advent-of-code-2025.day10
  (:require [common :refer [read-data get-numbers sum]]
            [clojure.string :as str]
            [clojure.math.combinatorics :as cmb]
            [fastmath.optimization :as opt]
            [fastmath.core :as m]))

(defn button->number [numbers]
  (reduce (fn [curr id] (bit-set curr id)) 0 numbers))

(defn lights->number [lights]
  (->> (map-indexed vector lights)
       (reduce (fn [buff [id l]] (if (= "#" l) (conj buff id) buff)) [])
       (button->number)))

(defn button->array [cnt numbers]
  (reduce (fn [curr id] (assoc curr id 1)) (vec (repeat cnt 0)) numbers))

(defn parse-line
  [line]
  (let [in (str/split line #"\s+")
        lights (re-seq #"[\#\.]" (first in))
        buttons (map get-numbers (butlast (rest in)))]
    {:target (lights->number lights)
     :buttons (map (partial button->array (count lights)) buttons)
     :button-ids (set (map button->number buttons))
     :joltage (get-numbers (last in))}))

(def data (map parse-line (read-data 2025 10)))

(defn xor [target buttons] (reduce bit-xor target buttons))

(defn find-minimal-buttons
  ([{:keys [target button-ids] :as data} nbuttons]
   (if (->> (cmb/combinations button-ids nbuttons)
            (map (partial xor target))
            (some zero?))
     nbuttons
     (recur data (inc nbuttons))))
  ([{:keys [target button-ids] :as data}]
   (if (button-ids target) 1 (find-minimal-buttons data 2))))

(def part-1 (sum (map find-minimal-buttons data)))
;; => 512

;; ILP based on LP and branch-and-bound algorithm https://web.mit.edu/15.053/www/AMP-Chapter-09.pdf (9.5)

(defn almost-int? [^double v] (m/near-zero? (- v (m/round v))))

(defn lp
  [target constraints]
  (try
    (opt/linear-optimization target constraints {:non-negative? true})
    ;; non feasible throws an exception, set score as infinite then
    (catch Exception _ [[] ##Inf])))

(defn subdivide
  "Subdivide problem to two separate cases, a_n <= floor(a_n) and a_n >= ceil(a_n) when a_n is not integer.
   Subdivide recursively and find a minimal score from all cases."
  [target eqs coeffs]
  (let [[[id z]] (->> (map-indexed vector coeffs)
                      (filter (comp (complement almost-int?) second)))
        branch-coefficient (assoc (vec (repeat (count coeffs) 0)) id 1)
        eqs1 (conj eqs branch-coefficient :<= (m/floor z)) ;; add two new constraints
        eqs2 (conj eqs branch-coefficient :>= (m/ceil z))
        [coeffs1 solution1] (lp target eqs1)
        [coeffs2 solution2] (lp target eqs2)]
    (min (if (every? almost-int? coeffs1) solution1 (subdivide target eqs1 coeffs1))
         (if (every? almost-int? coeffs2) solution2 (subdivide target eqs2 coeffs2)))))

(defn integer-linear-programming
  [{:keys [buttons joltage]}]
  (let [cnt (count buttons)
        target (conj (vec (repeat cnt 1)) 0) ;; minimize sum of coefficients
        eqs (vec (->> (apply map vector buttons)
                      (mapcat (fn [j v] [v := j]) joltage))) ;; constraints
        [coeffs solution] (lp target eqs)] ;; optimize
    (m/round (if (every? almost-int? coeffs) ;; if all coefficients are integers we got it, otherwise subdivide
               solution
               (subdivide target eqs coeffs)))))

(def part-2 (sum (map integer-linear-programming data)))
;; => 19857
