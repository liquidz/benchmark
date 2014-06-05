(ns benchmark.cuma-test
  (:require
    [clojure.test   :refer :all]
    [benchmark.cuma :refer :all]))


(deftest all-test
  (is (= (cuma-unescaped) (clostache-unescaped) (selmer-unescaped)))
  (is (= (cuma-escaped)   (clostache-escaped)   (selmer-escaped)))
  (is (= (cuma-dotted)    (clostache-dotted)    (selmer-dotted)))
  (is (= (cuma-if)        (clostache-if)        (selmer-if)))
  (is (= (cuma-for)       (clostache-for)       (selmer-for)))
  (is (= (cuma-map-bind)  (clostache-map-bind)))
  (is (= (cuma-partial)   (clostache-partial))))

