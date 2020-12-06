(ns advent-of-code-2020.day04-test
  (:require [advent-of-code-2020.day04 :as sut]
            [advent-of-code-2020.common :refer [str-as-blocks]]
            [clojure.test :as t]))

(def data (sut/parse-passports (str-as-blocks "ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
byr:1937 iyr:2017 cid:147 hgt:183cm

iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884
hcl:#cfa07d byr:1929

hcl:#ae17e1 iyr:2013
eyr:2024
ecl:brn pid:760753108 byr:1931
hgt:179cm

hcl:#cfa07d eyr:2025 pid:166559648
iyr:2011 ecl:brn hgt:59in")))

(defn passport-valid? [rec] (= 7 (count (select-keys rec sut/required-fields))))

(t/deftest passport-validity
  (t/is (passport-valid? (first data)))
  (t/is (not (passport-valid? (second data))))
  (t/is (passport-valid? (nth data 2)))
  (t/is (not (passport-valid? (nth data 3)))))

(t/deftest passport-validity-count
  (t/is (= 2 (count (sut/with-required data)))))

;;

(t/deftest field-validity
  (t/are [field value valid?] (= valid? (boolean ((sut/validators field) value)))
    :byr "2002" true
    :byr "2003" false
    :hgt "60in" true
    :hgt "190cm" true
    :hgt "190in" false
    :hgt "190" false
    :hcl "#123abc" true
    :hcl "#123abz" false
    :hcl "123abc" false
    :ecl "brn" true
    :ecl "wat" false
    :pid "000000001" true
    :pid "0123456789" false))

(defn valid-passport-and-fields?
  [passport]
  (and (passport-valid? passport)
       (sut/fields-valid? passport)))

(def data-valid (sut/parse-passports (str-as-blocks "pid:087499704 hgt:74in ecl:grn iyr:2012 eyr:2030 byr:1980
hcl:#623a2f

eyr:2029 ecl:blu cid:129 byr:1989
iyr:2014 pid:896056539 hcl:#a97842 hgt:165cm

hcl:#888785
hgt:164cm byr:2001 iyr:2015 cid:88
pid:545766238 ecl:hzl
eyr:2022

iyr:2010 hgt:158cm hcl:#b6652a ecl:blu byr:1944 eyr:2021 pid:093154719")))

(def data-invalid (sut/parse-passports (str-as-blocks "eyr:1972 cid:100
hcl:#18171d ecl:amb hgt:170 pid:186cm iyr:2018 byr:1926

iyr:2019
hcl:#602927 eyr:1967 hgt:170cm
ecl:grn pid:012533040 byr:1946

hcl:dab227 iyr:2012
ecl:brn hgt:182cm pid:021572410 eyr:2020 byr:1992 cid:277

hgt:59cm ecl:zzz
eyr:2038 hcl:74454a iyr:2023
pid:3556412378 byr:2007")))

(t/deftest passport-field-validity
  (t/is (every? identity (map valid-passport-and-fields? data-valid)))
  (t/is (every? (complement identity) (map valid-passport-and-fields? data-invalid))))
