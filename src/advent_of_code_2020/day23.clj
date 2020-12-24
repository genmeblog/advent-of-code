(ns advent-of-code-2020.day23)

(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)

(def data [3 6 2 9 8 1 7 5 4])

(defn make-destination
  [^long mx]
  (let [mx- (dec mx)]
    (fn [^long id ^long s1 ^long s2 ^long s3]
      (loop [i (dec id)]
        (let [ni (if (neg? i) mx- i)]
          (if (or (== ni s1)
                  (== ni s2)
                  (== ni s3))
            (recur (dec ni))
            ni))))))

(defn prepare-data
  [data ^long cnt]
  (let [data (map dec data)
        v (int-array (range 1 (inc cnt)))]
    (doseq [[^int a ^int b] (partition 2 1 data)]
      (aset ^ints v a b))
    (if (> cnt (count data))
      (do
        (aset ^ints v (dec cnt) ^int (first data))
        (aset ^ints v ^int (last data) 9))
      (aset ^ints v ^int (last data) ^int (first data)))
    [(first data) v]))

(defn step!
  [data destination ^long id]
  (let [s1 (aget ^ints data id)
        s2 (aget ^ints data s1)
        s3 (aget ^ints data s2)
        s4 (aget ^ints data s3)
        d (destination id s1 s2 s3)
        s5 (aget ^ints data d)]
    (aset ^ints data id s4)
    (aset ^ints data d s1)
    (aset ^ints data s3 s5)
    s4))

(defn game
  [data ^long size ^long cnt]
  (let [[^int start d] (prepare-data data size)
        destination (make-destination size)]
    (loop [id start
           idx (int 0)]
      (if (< idx cnt)
        (recur (step! d destination id) (inc idx))
        (->> (iterate (fn [^long i]
                        (aget ^ints d i)) 0)
             (next)
             (map inc))))))

(defn game-1
  [data]
  (->> (game data 9 100)
       (take 8)
       (apply str)
       (read-string)))

(def part-1 (game-1 data))
;; => 24798635

(defn game-2
  [data]
  (reduce * (take 2 (game data 1000000 10000000))))

(def part-2 (game-2 data))
;; => 12757828710

