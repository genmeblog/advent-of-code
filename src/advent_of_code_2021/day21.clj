(ns advent-of-code-2021.day21)

(reduce (fn [{:keys [current] :as m} [id [a b c]]]
          (let [[pos points] (m current)
                npos (mod (+ pos a b c) 10)
                npoints (+ points (inc npos))
                other-player (mod (inc current) 2)]
            (if (>= npoints 1000)
              (reduced (* (inc id) 3 (get-in m [other-player 1])))
              (assoc m :current other-player
                     current [npos npoints])))) {0 [9 0]
                                                 1 [6 0]
                                                 :current 0} (map-indexed vector (partition 3 (cycle (range 1 101)))))
;; => 906093



(def freqs (frequencies (for [a (range 1 4)
                            b (range 1 4)
                            c (range 1 4)]
                        (+ a b c))))
;; => {3 1, 4 3, 5 6, 6 7, 7 6, 8 3, 9 1}

;; 27

(def points (update-vals (group-by first (for [p (range 10)
                                             a (range 1 4)
                                             b (range 1 4)
                                             c (range 1 4)]
                                         [(inc p) (inc (mod (+ p a b c) 10))]))
                       #(frequencies (map second %))))

points
;; => {7 {10 1, 1 3, 2 6, 3 7, 4 6, 5 3, 6 1},
;;     1 {4 1, 5 3, 6 6, 7 7, 8 6, 9 3, 10 1},
;;     4 {7 1, 8 3, 9 6, 10 7, 1 6, 2 3, 3 1},
;;     6 {9 1, 10 3, 1 6, 2 7, 3 6, 4 3, 5 1},
;;     3 {6 1, 7 3, 8 6, 9 7, 10 6, 1 3, 2 1},
;;     2 {5 1, 6 3, 7 6, 8 7, 9 6, 10 3, 1 1},
;;     9 {2 1, 3 3, 4 6, 5 7, 6 6, 7 3, 8 1},
;;     5 {8 1, 9 3, 10 6, 1 7, 2 6, 3 3, 4 1},
;;     10 {3 1, 4 3, 5 6, 6 7, 7 6, 8 3, 9 1},
;;     8 {1 1, 2 3, 3 6, 4 7, 5 6, 6 3, 7 1}}

(def p1 (into {} (map (juxt first (partial apply hash-map )) (points 10))))
(into {} (map (juxt first (partial apply hash-map )) (points 10)))
;; => {3 {3 1}, 4 {4 3}, 5 {5 6}, 6 {6 7}, 7 {7 6}, 8 {8 3}, 9 {9 1}}


(defn move
  [m]
  (reduce (fn [curr [p [currp cnt]]]
            ()) m (keys m)))

