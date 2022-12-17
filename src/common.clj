(ns common
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn- format-name
  ([year day] (format-name year day ""))
  ([year day suff]
   (format (str "advent_of_code_" year "/day%02d%s.txt") day suff)))

(defn read-single-line
  [& args]
  (-> (apply format-name args)
      (io/resource)
      (slurp)
      (str/trim)))

(defn read-data
  [& args]
  (-> (apply format-name args)
      (io/resource)
      (io/reader)
      (line-seq)))

(defn str-as-blocks
  ([s] (str-as-blocks s true))
  ([s trim?]
   (->> (str/split s  #"\n\n")
        (map (fn [block] (let [sb (str/split block #"\n")]
                          (if trim? (map str/trim sb) sb)))))))

(defn read-data-as-blocks
  [& args]
  (-> (apply format-name args)
      (io/resource)
      (slurp)
      (str-as-blocks)))

(defn read-data-as-blocks-no-trim
  [& args]
  (-> (apply format-name args)
      (io/resource)
      (slurp)
      (str-as-blocks false)))

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
