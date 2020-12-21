(ns advent-of-code-2020.day21-test
  (:require [advent-of-code-2020.day21 :as sut]
            [clojure.test :as t]
            [clojure.string :as str]))

(def data (map sut/parser (str/split-lines "mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
trh fvjkl sbzzf mxmxvkd (contains dairy)
sqjhc fvjkl (contains soy)
sqjhc mxmxvkd sbzzf (contains fish)")))

(t/deftest example-menu
  (t/is (= 5 (sut/find-not-containing data)))
  (t/is (= "mxmxvkd,sqjhc,fvjkl" (sut/allergens data))))

(t/deftest sut
  (t/is (= 1913 sut/part-1))
  (t/is (= "gpgrb,tjlz,gtjmd,spbxz,pfdkkzp,xcfpc,txzv,znqbr" sut/part-2)))
