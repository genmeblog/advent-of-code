(ns advent-of-code-2020.day04
  (:require [advent-of-code-2020.common :refer [read-data]]
            [clojure.string :as str]))

(defn parse-line
  [line]
  (mapcat #(let [[k v] (str/split % #":")]
             [(keyword k) v]) (str/split line #" ")))

(defn parser
  [lines]
  (reduce (fn [[h & r :as maps] line]
            (if (seq line)
              (conj r (apply assoc h (parse-line line)))
              (conj maps {}))) '({}) lines))

(def required-fields [:byr :iyr :eyr :hgt :hcl :ecl :pid])

(def data (parser (read-data 4)))

(defn with-required
  [data]
  (->> data
       (map #(select-keys % required-fields))
       (filter #(= 7 (count %)))))

(def passports-with-required (with-required data))

(def part-1 (time (count passports-with-required)))
;; 196

(defn ->number [in] (try (if (= \0 (first in)) ;; avoid octal number
                           (Long/parseLong in)
                           (Long/decode in)) (catch Exception _)))
(defn between? [v1 v2 in] (when-let [n (->number in)] (<= v1 n v2)))
(defn height [in]
  (let [[w m] (rest (re-find #"(.*)(..)$" in))]
    (or (and (= m "cm") (between? 150 193 w))
        (and (= m "in") (between? 59 76 w)))))

(def validators {:byr (partial between? 1920 2002)
                 :iyr (partial between? 2010 2020)
                 :eyr (partial between? 2020 2030)
                 :hgt height
                 :hcl #(and (re-find #"^#[0-9a-f]{6}$" %) (->number %))
                 :ecl #{"amb" "blu" "brn" "gry" "grn" "hzl" "oth"}
                 :pid #(and (re-find #"^\d{9}$" %) (->number %))
                 :cid (constantly true)})

(defn fields-valid? [passport] (reduce-kv (fn [p k v] (and p ((validators k) v))) true passport))

(def part-2 (time (->> passports-with-required
                       (map fields-valid?)
                       (filter identity)
                       (count))))
;; => 114
