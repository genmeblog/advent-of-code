(ns advent-of-code-2023.day03-test
  (:require [advent-of-code-2023.day03 :as sut]
            [clojure.test :as t]))

(def data-1 (sut/parse-data ["467..114.."
                           "...*......"
                           "..35..633."
                           "......#..."
                           "617*......"
                           ".....+.58."
                           "..592....."
                           "......755."
                           "...$.*...."
                           ".664.598.."]))

(t/deftest example
  (t/is (= 4361 (sut/part-numbers data-1)))
  (t/is (= 467835 (sut/gears-ratios data-1))))

(t/deftest sut
  (t/is (= 520135 sut/part-1))
  (t/is (= 72514855 sut/part-2)))
