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

(deftest next-coordinate
  (testing "Test retrieving next coordinate"
    (is (= [0 1] (snail/next-coordinate :right [0 0])))
    (is (= [2 1] (snail/next-coordinate :left [2 2])))
    (is (= [2 2] (snail/next-coordinate :down [1 2])))
    (is (= [1 0] (snail/next-coordinate :up [2 0])))))
