(ns advent-of-code-2019.day03-test
  (:require [advent-of-code-2019.day03 :refer :all]
            [clojure.test :refer :all]))

(def id
  {:a [2 0 2 3 :h 1 1]
   :b1 [2 1 3 1 :v 1 2]
   :b2 [2 3 1 1 :v 1 3]
   :c [1 0 1 2 :v 1 4]
   :d [1 3 4 4 :h 1 5]
   :e [2 1 3 3 :v 1 6]
   :f [2 2 4 2 :h 1 7]})

(deftest intersection-test
  (is (= [4 1 3 1 1] (intersection (:a id) (:b1 id))))
  (is (nil? (intersection (:b1 id) (:a id))))
  (is (= [4 1 3 1 1] (intersection (:a id) (:b2 id))))
  (is (nil? (intersection (:b2 id) (:a id))))
  (is (nil? (intersection (:b1 id) (:c id))))
  (is (nil? (intersection (:c id) (:b1 id))))
  (is (= [5 3 2 1 1] (intersection (:e id) (:f id))))
  (is (nil? (intersection (:f id) (:e id))))
  (is (nil? (intersection (:d id) (:d id)))))

(def wires-1 (mapcat process-wire [[0 "R75,D30,R83,U83,L12,D49,R71,U7,L72"]
                                   [1 "U62,R66,U55,R34,D71,R55,D58,R83"]]))
(def wires-2 (mapcat process-wire [[0 "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51"]
                                   [1 "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"]]))
(def wires-3 (mapcat process-wire [[0 "R8,U5,L5,D3"]
                                   [1 "U7,R6,D4,L4"]]))

(deftest minimum-distance-test
  (is (= 159 (minimum-distance (intersections wires-1))))
  (is (= 135 (minimum-distance (intersections wires-2))))
  (is (= 6 (minimum-distance (intersections wires-3)))))

(deftest minimum-lengths-test
  (is (= 610 (minimum-lengths (intersections wires-1) (group-by last wires-1))))
  (is (= 410 (minimum-lengths (intersections wires-2) (group-by last wires-2))))
  (is (= 30 (minimum-lengths (intersections wires-3) (group-by last wires-3)))))

(deftest results
  (is (= 1519 part-1))
  (is (= 14358 part-2)))
