(ns advent-of-code-2020.day23)

(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)

(def data [3 6 2 9 8 1 7 5 4])

(defn make-destination
  [^long mx]
  (fn [^long id ^long s1 ^long s2 ^long s3]
    (loop [i (dec id)]
      (let [ni (if (zero? i) mx i)]
        (if (or (== ni s1)
                (== ni s2)
                (== ni s3))
          (recur (dec ni))
          ni)))))

(def destination-9 (make-destination 9))

(defn step-game1
  [data]
  (let [v (first data)
        phase1 (next data)
        phase2 (vec (take 3 phase1))
        phase3 (take 6 (conj (drop 3 phase1) v))
        nxt (destination-9 v (phase2 0) (phase2 1) (phase2 2))
        [a b] (split-with (complement #{nxt}) phase3)]
    (take 9 (next (cycle (concat a [nxt] phase2 (rest b)))))))

(defn first-game
  [data]
  (read-string (apply str (take 8 (rest (second (split-with (complement #{1}) (cycle (nth (iterate step-game1 data) 100)))))))))

(def part-1 (first-game data))
;; => 24798635

(defn find-id
  ^long [data ^long id]
  (loop [idx (int 0)]
    (if (= id (aget ^ints data idx))
      idx
      (recur (inc idx)))))

(def destination-m (make-destination 1000000))

(defn step-game2
  [data]
  (let [v (aget ^ints data 0)
        s1 (aget ^ints data 1)
        s2 (aget ^ints data 2)
        s3 (aget ^ints data 3)
        nxt (destination-m v s1 s2 s3)
        pos (find-id data nxt)
        diff (- pos 3)]
    (System/arraycopy data 4 data 0 diff)
    (System/arraycopy data pos data (dec pos) (- 1000000 pos))
    (aset ^ints data diff s1)
    (aset ^ints data (inc diff) s2)
    (aset ^ints data (+ diff 2) s3)
    (aset ^ints data 999999 v)
    data))

(def data [3 8 9 1 2 5 4 6 7])

(time (let [^ints v (nth (iterate step-game2 (int-array (concat data (range 10 1000001)))) 33)
            pos (inc (find-id v 1))]
        (* (aget v pos) (aget v (inc pos)))))


