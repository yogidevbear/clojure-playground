(ns chapter4.fp-test
  (:require [clojure.test :refer :all]
            [chapter4.fp :as fp]))

(deftest stack-consuming-fibo
  (testing "Test chapter4.fp/stack-consuming-fibo"
    (is (= 34 (fp/stack-consuming-fibo 9)))))