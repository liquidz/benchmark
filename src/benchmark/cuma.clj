(ns benchmark.cuma
  (:require
    [benchmark.core   :refer [*full-bench* exec-time-mean]]
    [clojure.string   :as str]
    [clostache.parser :as clostache]
    [cuma.core        :as cuma]))

(defn cuma-unescaped [] (cuma/render "$(raw x)" {:x "<h1>"}))
(defn cuma-escaped   [] (cuma/render "$(x)" {:x "<h1>"}))
(defn cuma-dotted    [] (cuma/render "$(x.y)" {:x {:y "hello"}}))
(defn cuma-if        [] (cuma/render "@(if x)hello@(end)" {:x true}))
(defn cuma-for       [] (cuma/render "@(for arr)$(n)@(end)" {:arr [{:n 1} {:n 2} {:n 3}]}))
(defn cuma-map-bind  [] (cuma/render "@(if m)$(n)@(end)" {:m {:n 100}}))
(defn cuma-partial   [] (cuma/render "$(include tmpl)" {:tmpl "hello $(x)" :x "world"}))
(defn cuma-let       [] (cuma/render "@(let :x \"foo\")$(x)@(end)" {}))

(defn clostache-unescaped [] (clostache/render "{{&x}}" {:x "<h1>"}))
(defn clostache-escaped   [] (clostache/render "{{x}}" {:x "<h1>"}))
(defn clostache-dotted    [] (clostache/render "{{&x.y}}" {:x {:y "hello"}}))
(defn clostache-if        [] (clostache/render "{{#x}}hello{{/x}}" {:x true}))
(defn clostache-for       [] (clostache/render "{{#arr}}{{n}}{{/arr}}" {:arr [{:n 1} {:n 2} {:n 3}]}))
(defn clostache-map-bind  [] (clostache/render "{{#m}}{{n}}{{/m}}" {:m {:n 100}}))
(defn clostache-partial   [] (clostache/render "{{>tmpl}}" {:x "world"} {:tmpl "hello {{x}}"}))

(defn -main
  [& [full-bench]]
  (binding [*full-bench* (some? full-bench)]
    (println "## cuma")
    (->> [(exec-time-mean (cuma-unescaped))
          (exec-time-mean (cuma-escaped))
          (exec-time-mean (cuma-dotted))
          (exec-time-mean (cuma-if))
          (exec-time-mean (cuma-for))
          (exec-time-mean (cuma-map-bind))
          (exec-time-mean (cuma-partial))
          (exec-time-mean (cuma-let))]
         (str/join "\n")
         println)

    (println "## clostache")
    (->> [(exec-time-mean (clostache-unescaped))
          (exec-time-mean (clostache-escaped))
          (exec-time-mean (clostache-dotted))
          (exec-time-mean (clostache-if))
          (exec-time-mean (clostache-for))
          (exec-time-mean (clostache-map-bind))
          (exec-time-mean (clostache-partial))]
         (str/join "\n")
         println)))
