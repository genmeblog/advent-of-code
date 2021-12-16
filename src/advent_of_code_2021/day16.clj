(ns advent-of-code-2021.day16
  (:require [clojure.string :as str]
            [common :refer [read-single-line]]))

(defn ->binary-seq [input]
  (mapcat #(str/replace (->> (Character/digit % 16)
                             (Integer/toBinaryString)
                             (format "%4s"))
                        #"\s" "0")
          input))

(defn ->long [s]
  (if (seq? (first s))
    (->long (flatten s))
    (Long/parseLong (apply str s) 2)))

(defn valid-stream? [s c] (>= (count s) c))

(defn parse-start
  [stream]
  (let [[version rst] (split-at 3 stream)
        [id rst] (split-at 3 rst)]
    [{:version (->long version) :id (->long id)} rst]))

(defn parse-number-part
  [stream ctx]
  (let [[[b & n] rst] (split-at 5 stream)]
    [b (update ctx :number conj n) rst]))

(defn bits-parser
  ([stream] (bits-parser stream -1))
  ([stream subp] (bits-parser stream :start [] nil subp))
  ([stream token result ctx subp]
   (if (or (not (seq stream))
           (zero? subp)
           (< (count stream) 5))
     [result stream]
     (case token
       :start (let [[nctx rst] (parse-start stream)]
                (if (= (nctx :id) 4)
                  (recur rst :number result (assoc nctx :number []) subp)
                  (recur rst :op result nctx subp)))
       :number (let [[b nctx rst] (parse-number-part stream ctx)]
                 (if (= b \1)
                   (recur rst :number result nctx subp)
                   (recur rst :start (conj result (update nctx :number ->long)) nil (dec subp))))
       :op (let [[b & rst] stream]
             (if (= b \0)
               (let [[cnt rst] (split-at 15 rst)
                     [sub rst] (split-at (->long cnt) rst)]
                 (recur rst :start (conj result (assoc ctx :subpacket (first (bits-parser sub)))) nil (dec subp)))
               (let [[cnt rst] (split-at 11 rst)
                     [sub rst] (bits-parser rst (->long cnt))]
                 (recur rst :start (conj result (assoc ctx :subpacket sub)) nil (dec subp)))))))))

(defn parse [data] (-> data ->binary-seq bits-parser first))

(def data (parse (read-single-line 2021 16)))

(defn count-versions [input]
  (reduce #(+ %1 (:version %2) (count-versions (:subpacket %2))) 0 input))

(def part-1 (count-versions data))
;; => 893

(defn execute [{:keys [id subpacket] :as input}]
  (cond
    (= id 4) (:number input)
    (< id 4)(reduce (case id 0 + 1 * 2 min 3 max) (map execute subpacket))
    :else (if ((case id 5 > 6 < 7 =) (execute (first subpacket)) (execute (second subpacket))) 1 0)))

(def part-2 (execute (first data)))
;; => 4358595186090
