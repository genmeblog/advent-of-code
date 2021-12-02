(ns advent-of-code-2017.day04
  (:require [common :refer [read-data]]
            [clojure.string :as str]))

(def data (map #(str/split % #"\s+") (read-data 2017 4)))

(defn valid? [passphrase] (= (count passphrase) (count (set passphrase))))

(def part-1 (count (filter valid? data)))
;; => 455

(defn valid2? [passphrase] (valid? (map sort passphrase)))

(def part-2 (count (filter valid2? data)))
;; => 186
