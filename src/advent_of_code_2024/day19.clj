(ns advent-of-code-2024.day19
  (:require [common :refer [read-data-as-blocks sum]]
            [clojure.string :as str]
            [clojure.pprint :as pp]))

(defn parse [[towels designs]] [(re-seq #"\w+" (first towels))
                             (map vector designs)])

(def data (parse (read-data-as-blocks "examples" 2024 19)))

(defn search-one [towels design]
  (reduce (fn [buff towel]
            (if (str/starts-with? design towel)
              (conj buff (subs design (count towel)))
              buff)) [] towels))

(defn msearch [towels]
  (memoize (fn [design] (search-one towels design))))

(defn search [ms towels target]
  (if-let [target (seq (filter seq target))]
    (if-let [ntarget (seq (mapcat ms target))]
      (recur ms towels ntarget)
      0)
    1))

(sum (map (partial search (msearch (first data)) (first data)) [(first (second data))]))


(group-by count (first data))



(clean-towels (sort-by count (first data)))

(str/replace "asdfsdf" "a" "")
