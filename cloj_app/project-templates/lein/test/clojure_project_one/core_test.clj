(ns clojure-project-one.core-test
  (:require [clojure.test :refer [deftest is testing]]
            [clojure-project-one.core :as core]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 0 (core/foo 1)))))
