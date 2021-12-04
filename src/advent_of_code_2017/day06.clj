(ns advent-of-code-2017.day06)

(def data [5 1 10 0 1 7 13 14 3 12 8 10 7 12 0 6])

(defn find-max
  [data]
  (reduce #(if (> (second %2) (second %1)) %2 %1 ) [-1 -1] (map-indexed vector data)))

(defn distribute
  [data]
  (let [[id v] (find-max data)
        cnt (count data)
        id+ (inc id)]
    (reduce #(update %1 (mod (+ id+ %2) cnt) inc) (assoc data id 0) (range v))))

(defn iterator
  ([data] (iterator data #{data}))
  ([data buff]
   (let [new-data (distribute data)]
     (if (buff new-data)
       [(count buff) new-data]
       (recur new-data (conj buff new-data))))))

(def part-1 (first (iterator data)))
;; => 5042

(def part-2 (first (iterator (second (iterator data)))))
;; => 1086
