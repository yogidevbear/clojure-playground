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

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 1))))
