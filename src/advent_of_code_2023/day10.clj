(ns advent-of-code-2023.day10
  (:require [common :refer [read-data]]
            [clojure.string :as str]
            [clojure2d.core :as c2d]
            [clojure2d.extra.utils :as utils]))

(def connections {#{:N :S} \| #{:W :E} \-
                #{:S :E} \F #{:S :W} \7
                #{:N :E} \L #{:N :W} \J})

(def rules {[\- :W] :W [\- :E] :E
          [\| :N] :N [\| :S] :S
          [\L :S] :E [\L :W] :N
          [\F :N] :E [\F :W] :S
          [\J :S] :W [\J :E] :N
          [\7 :E] :S [\7 :N] :W})

(defn starting-point [data]
  (reduce (fn [row line]
            (if-let [scol (str/index-of line "S")]
              (reduced [row scol])
              (inc row))) 0 data))

(defn move-dir [[x y] dir]
  (case dir
    :W [x (dec y)]
    :E [x (inc y)]
    :N [(dec x) y]
    :S [(inc x) y]))

(defn lookup [data pos dir] (get-in data (move-dir pos dir)))

(defn starting-point-and-dir [data]
  (let [pos (starting-point data)
        dir (->> [[:N #{\| \F \7}]
                  [:S #{\| \L \J}]
                  [:W #{\- \F \L}]
                  [:E #{\- \J \7}]]
                 (map (fn [[m s]] (when (s (lookup data pos m)) m)))
                 (filter identity)
                 (set))]
    [pos (first dir) (connections dir)]))

(defn make-step [data [pos dir]]
  (let [npos (move-dir pos dir)
        pipe (get-in data npos)]
    [npos (rules [pipe dir])]))

(defn build-loop [data]
  (let [[spoint :as s] (starting-point-and-dir data)]
    [(conj (->> (iterate (partial make-step data) s)
                (rest)
                (take-while #(not= spoint (first %)))
                (map first)
                (set)) spoint) s]))

(def data (vec (read-data 2023 10)))

(defn farthest-point [data]
  (-> data build-loop first count (quot 2)))

(def part-1 (farthest-point data))
;; => 6682

(defn find-interior [data]
  (let [[b [spos _ spipe]] (build-loop data)]
    (->> data
         (map-indexed (fn [row l]
                        (->> (map-indexed vector l)
                             (reduce (fn [[inside? cnt ctx :as curr] [col ch]]
                                       (let [pos [row col]
                                             ch (if (= pos spos) spipe ch)]
                                         (cond
                                           (not (b pos)) [inside? (if inside? (inc cnt) cnt) ctx]
                                           (#{\F \L} ch) [inside? cnt ch]
                                           (or (and (= ch \7) (= ctx \F))
                                               (and (= ch \J) (= ctx \L))) [inside? cnt nil]
                                           (or (= ch \|)
                                               (and (= ch \7) (= ctx \L))
                                               (and (= ch \J) (= ctx \F))) [(not inside?) cnt nil]
                                           :else curr))) [false 0 nil]))))
         (map second)
         (reduce +))))

(def part-2 (find-interior data))
;; => 353

;;;; viz

(let [w (count (first data))
      h (count data)
      b (first (build-loop data))]
  (c2d/with-canvas [c (c2d/canvas (* w 3) (* h 3) :low)]
    (c2d/set-background c :docc/dull-violet-black)
    (doseq [x (range w)
            y (range h)
            :let [pos [y x]
                  xx (inc (* x 3))
                  yy (inc (* y 3))
                  ch (get-in data pos)]]
      (c2d/set-color c (cond
                         (= \S ch) :white
                         (b pos) :docc/orange-yellow
                         :else :docc/vandar-poel-s-blue))
      (c2d/point c xx yy)
      (condp = ch
        \| (do (c2d/point c xx (inc yy))
               (c2d/point c xx (dec yy)))
        \- (do (c2d/point c (inc xx) yy)
               (c2d/point c (dec xx) yy))
        \J (do (c2d/point c (dec xx) yy)
               (c2d/point c xx (dec yy)))
        \L (do (c2d/point c (inc xx) yy)
               (c2d/point c xx (dec yy)))
        \7 (do (c2d/point c (dec xx) yy)
               (c2d/point c xx (inc yy)))
        \F (do (c2d/point c (inc xx) yy)
               (c2d/point c xx (inc yy)))
        \S (c2d/crect c (inc xx) (inc yy) 3 3)
        nil))
    #_(c2d/save c "images/advent_of_code_2023/day10.png")
    (utils/show-image c)))
