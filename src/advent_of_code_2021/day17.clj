(ns advent-of-code-2021.day17)

(defn scan
  [{[mnx mxx] :x [mny mxy] :y}]
  (-> (for [x (range (inc mxx)) ;; x search up to mxx
            y (range mny (/ mxx 2)) ;; y search cant be lower than bottom, can't be higher than half of right border
            :let [xs (reductions + (concat (range x 0 -1) (repeat 0)))
                  ys (reductions + (range y (dec mny) -1))]
            :when (some (fn [[xx yy]]
                          (and (<= mnx xx mxx)
                               (<= mny yy mxy))) (map vector xs ys))]
        (reduce max ys))))

(def data (scan {:x [195 238] :y [-93 -67]}))

(def part-1 (reduce max data))
;; => 4278

(def part-2 (count data))
;; => 1994
