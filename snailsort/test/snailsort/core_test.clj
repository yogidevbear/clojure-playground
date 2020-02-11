(ns snailsort.core-test
  (:require [clojure.test :refer :all]
            [snailsort.core :as snail]))

; Reference coordinates
;   x y
; [[0 0] [0 1] [0 2]
;  [1 0] [1 1] [1 2]
;  [2 0] [2 1] [2 2]]

(def grid
  [[1 2 3]
   [8 9 4]
   [7 6 5]])

(def grid-alt-1
  [[1 2]
   [4 3]])

(def grid-alt-2
  [[1 2 3 4 5]
   [16 17 18 19 6]
   [15 24 25 20 7]
   [14 23 22 21 8]
   [13 12 11 10 9]])

(defn generate-grid
  "Generates a random n x n grid"
  [n]
  (if (< n 1)
    [[]]
    (let [row (fn [] (take n (repeatedly #(rand-int 10))))]
      (into [] (take n (repeatedly #(into [] (row))))))))

(deftest is-valid-grid?
  (testing "Test if grid supplied is valid n x n square"
    (is (= false (snail/is-valid-grid? [])))
    (is (= false (snail/is-valid-grid? [1 2 3])))
    (is (= false (snail/is-valid-grid? [[1 2 3] [4 5 6]])))
    (is (= false (snail/is-valid-grid? [[1 2 3] [4 5] [6 7 8 9]])))
    (is (= true (snail/is-valid-grid? grid)))
    (is (= true (snail/is-valid-grid? (generate-grid 0))))
    (is (= true (snail/is-valid-grid? (generate-grid 3))))
    (is (= true (snail/is-valid-grid? (generate-grid 9))))
    (is (= true (snail/is-valid-grid? (generate-grid 100))))
    (is (= true (snail/is-valid-grid? (generate-grid 1000))))))

(deftest next-coordinate
  (testing "Test retrieving next coordinate"
    (is (= [0 1] (snail/next-coordinate :right [0 0])))
    (is (= [2 1] (snail/next-coordinate :left [2 2])))
    (is (= [2 2] (snail/next-coordinate :down [1 2])))
    (is (= [1 0] (snail/next-coordinate :up [2 0])))))

(deftest is-valid?
  (testing "Test if next coordinate is valid"
    (is (= true (snail/is-valid? grid [0 2])))
    (is (= true (snail/is-valid? grid [1 1])))
    (is (= false (snail/is-valid? grid [0 3])))
    (is (= false (snail/is-valid? grid [0 3])))))

(deftest next-direction
  (testing "Test next-direction for clockwise rotation"
    (is (= :down (snail/next-direction :right)))
    (is (= :left (snail/next-direction :down)))
    (is (= :up (snail/next-direction :left)))
    (is (= :right (snail/next-direction :up)))))

(deftest first-visit?
  (testing "Test if coordinate is being visited for the first time"
    (is (= true (snail/first-visit? #{} [0 0])))
    (is (= false (snail/first-visit? #{[0 0]} [0 0])))
    (is (= true (snail/first-visit? #{[0 0] [0 1]} [0 2])))
    (is (= false (snail/first-visit? #{[0 0] [0 1] [0 2]} [0 2])))))

(deftest all-visits?
  (testing "Test if visited set size matches grid size"
    (is (= false (snail/all-visits? grid #{[0 0]})))
    (is (= false (snail/all-visits? grid #{[0 0] [0 1]})))
    (is (= true (snail/all-visits? grid #{[0 0] [0 1] [0 2] [1 0] [1 1] [1 2] [2 0] [2 1] [2 2]})))
    (is (= true (snail/all-visits? grid #{[0 0] [0 1] [0 2] [1 2] [2 2] [2 1] [2 0] [1 0] [1 1]})))))

(deftest next-valid-coordinate
  (testing "Test next valid position in grid"
    (is (= {:direction :right :next-coordinate [0 1]} (snail/next-valid-coordinate grid [0 0] :right #{[0 0]})))
    (is (= {:direction :left :next-coordinate [2 1]} (snail/next-valid-coordinate grid [2 2] :down #{[0 0] [0 1] [0 2] [1 2] [2 2]})))
    (is (= {:direction :up :next-coordinate [1 0]} (snail/next-valid-coordinate grid [2 0] :left #{[0 0] [0 1] [0 2] [1 2] [2 2] [2 1] [2 0]})))
    (is (= {:direction :right :next-coordinate [1 1]} (snail/next-valid-coordinate grid [1 0] :up #{[0 0] [0 1] [0 2] [1 2] [2 2] [2 1] [2 0] [1 0]})))
    (is (= nil (snail/next-valid-coordinate grid [1 1] :down #{[0 0] [0 1] [0 2] [1 2] [2 2] [2 1] [2 0] [1 0] [1 1]})))))

(deftest sequence-path
  (testing "Test if sequence-path returns the correct vector sequence of coordinate points of snailsort"
    (is (= [[0 0] [0 1] [0 2] [1 2] [2 2] [2 1] [2 0] [1 0] [1 1]] (snail/sequence-path grid)))
    (is (= [[0 0] [0 1] [1 1] [1 0]] (snail/sequence-path grid-alt-1)))
    (is (= [[0 0] [0 1] [0 2] [0 3] [0 4] [1 4] [2 4] [3 4] [4 4] [4 3] [4 2] [4 1] [4 0] [3 0] [2 0] [1 0] [1 1] [1 2] [1 3] [2 3] [3 3] [3 2] [3 1] [2 1] [2 2]] (snail/sequence-path grid-alt-2)))))