(ns common
  (:require [clojure.java.io :as io]
            [clojure.string :as str])
  (:import [org.h2.mvstore MVMap MVStore MVStore$Builder]))

;; external kv-store 

(defmacro with-kv-store
  "H2 store encrypted with content of .data.h2.key file"
  [m map-name & forms]
  `(with-open [^MVStore store# (-> (MVStore$Builder.)
                                   (.fileName "resources/data.h2")
                                   (.encryptionKey (char-array (slurp ".data.h2.key")))
                                   (.compress)
                                   (.open))]
     (let [^MVMap ~m (.openMap store# ~map-name)]
       ~@forms)))

(defn sget
  ([k] (sget "inputs" k))
  ([map-name k] (with-kv-store m map-name (.get m k))))

(defn sset
  ([k v] (sset "inputs" k v))
  ([map-name k v] (with-kv-store m map-name (.put m k v))))

(defn add-all-data
  "Store all files from data/map-name as k=yyyydd and file content under"
  ([] (add-all-data "inputs"))
  ([map-name]
   (with-kv-store m map-name
     (doseq [file (rest (file-seq (io/file (str "data/" map-name))))
             :let [k (->> file str (re-seq #"\d+") first)]]
       (when-not (.containsKey m k)
         (.put m k (slurp file)))))))

;;

(defn format-name ([year day suff] (format (str year "%02d" suff) day)))

(defn read-single-line
  ([year day] (read-single-line "inputs" year day))
  ([map-name year day] (read-single-line map-name year day ""))
  ([map-name year day suff]
   (->> (format-name year day suff)
        (sget map-name)
        (str/trim))))

(defn read-data
  ([year day] (read-data "inputs" year day))
  ([map-name year day] (read-data map-name year day ""))
  ([map-name year day suff]
   (->> (format-name year day suff)
        (sget map-name)
        (char-array)
        (io/reader)
        (line-seq))))

(defn str-as-blocks
  ([s] (str-as-blocks s true))
  ([s trim?]
   (->> (str/split s  #"\n\n")
        (map (fn [block] (let [sb (str/split block #"\n")]
                          (if trim? (map str/trim sb) sb)))))))

(defn read-data-as-blocks
  ([year day] (read-data-as-blocks "inputs" year day))
  ([map-name year day] (read-data-as-blocks map-name year day ""))
  ([map-name year day suff]
   (->> (format-name year day suff)
        (sget map-name)
        (str-as-blocks))))

(defn read-data-as-blocks-no-trim
  ([year day] (read-data-as-blocks-no-trim "inputs" year day))
  ([map-name year day] (read-data-as-blocks-no-trim map-name year day ""))
  ([map-name year day suff]
   (str-as-blocks (->> (format-name year day suff)
                       (sget map-name)) false)))

(defn parse
  ([s] (mapv read-string s))
  ([re s] (mapv read-string (rest (re-find re s)))))

(defn split-line
  ([line] (split-line line #"\s+"))
  ([line re] (str/split (str/trim line) re)))

(defn ->bfs
  [around-fn start end]
  (fn local-bfs
    ([] (local-bfs [(conj clojure.lang.PersistentQueue/EMPTY start) #{start} {}]))
    ([[[v :as q] explored path]]
     (if (and v (not= v end))
       (recur (reduce (fn [[q explored path :as buff] pos]
                        (if-not (explored pos)
                          [(conj q pos)
                           (conj explored pos)
                           (assoc path pos v)]
                          buff)) [(pop q) explored path] (around-fn v)))
       path))))

(defn bfs
  [around-fn start end]
  ((->bfs around-fn start end)))

(defn bfs->path
  [around-fn start end]
  (->> (iterate (bfs around-fn start end) end)
       (take-while (complement #{start}))))

(defn bfs->path-count
  [around-fn start end]
  (count (bfs->path around-fn start end)))

;;

(defn- maybe-remove [m k]
  (if-not (seq (m k)) (dissoc m k) m))

(deftype PriorityQueue [priorities index]
  clojure.lang.IPersistentStack
  (peek [_] (first (second (first priorities))))
  (pop [_] (let [[k vs] (first priorities)
                 v (first vs)]
             (PriorityQueue. (-> priorities
                                 (update k disj v)
                                 (maybe-remove k))
                             (dissoc index v))))
  clojure.lang.Seqable
  (seq [_] (keys index))
  clojure.lang.IPersistentSet
  (contains [_ k] (contains? index k))
  clojure.lang.IFn
  (invoke [_ k] (index k)))

(defn set-priority
  ([pq [value priority]] (set-priority pq value priority))
  ([^PriorityQueue pq value priority]
   (PriorityQueue. (if-let [old-priority ((.index pq) value)]
                     (-> (.priorities pq)
                         (update old-priority disj value)
                         (maybe-remove old-priority)
                         (update priority (comp set conj) value))
                     (-> (.priorities pq)
                         (update priority (comp set conj) value)))
                   (assoc (.index pq) value priority))))

(defn priority [^PriorityQueue pq value] ((.index pq) value))

(defn ->priority-queue
  ([] (PriorityQueue. (sorted-map) {}))
  ([priorities] (reduce set-priority (->priority-queue) priorities)))

;;

(defn get-numbers [data]
  (mapv parse-long (re-seq #"\d+" data)))

;;


(comment
  (do
    (add-all-data)
    (add-all-data "examples")))
