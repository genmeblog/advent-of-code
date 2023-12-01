(ns advent-of-code-2022.day21
  (:require [common :refer [read-data split-line]]))

(defn parse [line] (split-line line #":\s|\s"))

(def data (map parse (read-data 2022 21)))

(defmacro build-oparations
  []
  `(do ~@(for [[f a op b] data
               :let [f (symbol f)]]
           (if-not op
             `(def ~f (constantly ~(parse-long a)))
             (let [[a op b] (map symbol [a op b])]
               `(do (declare ~a) (declare ~b)
                    (defn ~f [] (~op (~a) (~b)))))))))

(build-oparations)
(root)
;; => 83056452926300



(def data2 (map parse ["root: pppw + sjmn"
                     "dbpl: 5"
                     "cczh: sllz + lgvd"
                     "zczc: 2"
                     "ptdq: humn - dvpt"
                     "dvpt: 3"
                     "lfqf: 4"
                     "humn: 5"
                     "ljgn: 2"
                     "sjmn: drzm * dbpl"
                     "sllz: 4"
                     "pppw: cczh / lfqf"
                     "lgvd: ljgn * ptdq"
                     "drzm: hmdt - zczc"
                     "hmdt: 32"]))
