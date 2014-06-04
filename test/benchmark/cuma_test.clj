(ns benchmark.cuma-test
  (:require
    [clojure.test   :refer :all]
    [benchmark.cuma :refer :all]))


(deftest all-test
  (are [x y] (= x y)
       (cuma-unescaped) (clostache-unescaped)
       (cuma-escaped)   (clostache-escaped)
       (cuma-dotted)    (clostache-dotted)
       (cuma-if)        (clostache-if)
       (cuma-for)       (clostache-for)
       (cuma-map-bind)  (clostache-map-bind)
       (cuma-partial)   (clostache-partial)))

