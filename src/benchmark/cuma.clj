(ns benchmark.cuma
  (:require
    [benchmark.core   :refer [*full-bench* exec-time-mean print-mode]]
    [criterium.core   :as crit]
    [clojure.string   :as str]
    [clojure.java.io  :as io]
    [clostache.parser :as clostache]
    [selmer.parser    :as selmer]
    [cuma.core        :as cuma]))

(defn cuma-unescaped [] (cuma/render "$(raw x)" {:x "<h1>"}))
(defn cuma-escaped   [] (cuma/render "$(x)" {:x "<h1>"}))
(defn cuma-dotted    [] (cuma/render "$(x.y)" {:x {:y "hello"}}))
(defn cuma-if        [] (cuma/render "@(if x)hello@(end)" {:x true}))
(defn cuma-for       [] (cuma/render "@(for arr)$(n)@(end)" {:arr [{:n 1} {:n 2} {:n 3}]}))
(defn cuma-map-bind  [] (cuma/render "@(if m)$(n)@(end)" {:m {:n 100}}))
(defn cuma-partial   [] (cuma/render "$(include tmpl)" {:tmpl "hello $(x)" :x "world"}))
(defn cuma-let       [] (cuma/render "@(let :x \"foo\")$(x)@(end)" {}))
; use `slurp` to realize same condition of `selmer/render-file`
(defn cuma-layout    [] (cuma/render (slurp "test/files/cuma.tpl") {:x "x" :y "y"}))

(defn clostache-unescaped [] (clostache/render "{{&x}}" {:x "<h1>"}))
(defn clostache-escaped   [] (clostache/render "{{x}}" {:x "<h1>"}))
(defn clostache-dotted    [] (clostache/render "{{&x.y}}" {:x {:y "hello"}}))
(defn clostache-if        [] (clostache/render "{{#x}}hello{{/x}}" {:x true}))
(defn clostache-for       [] (clostache/render "{{#arr}}{{n}}{{/arr}}" {:arr [{:n 1} {:n 2} {:n 3}]}))
(defn clostache-map-bind  [] (clostache/render "{{#m}}{{n}}{{/m}}" {:m {:n 100}}))
(defn clostache-partial   [] (clostache/render "{{>tmpl}}" {:x "world"} {:tmpl "hello {{x}}"}))

(-> "." io/file .getAbsolutePath selmer/set-resource-path!)
;(selmer/cache-off!)
(defn selmer-unescaped [] (selmer/render "{{x|safe}}" {:x "<h1>"}))
(defn selmer-escaped   [] (selmer/render "{{x}}" {:x "<h1>"}))
(defn selmer-dotted    [] (selmer/render "{{x.y}}" {:x {:y "hello"}}))
(defn selmer-if        [] (selmer/render "{%if x%}hello{%endif%}" {:x true}))
(defn selmer-for       [] (selmer/render "{%for x in arr%}{{x.n}}{%endfor%}" {:arr [{:n 1} {:n 2} {:n 3}]}))
(defn selmer-layout    [] (selmer/render-file "test/files/selmer.tpl" {:x "x" :y "y"}))

(defn print-cuma-execution-mean-time
  []
  (print-mode)
  (println "## cuma")
  (println (exec-time-mean (cuma-unescaped)))
  (println (exec-time-mean (cuma-escaped)))
  (println (exec-time-mean (cuma-dotted)))
  (println (exec-time-mean (cuma-if)))
  (println (exec-time-mean (cuma-for)))
  (println (exec-time-mean (cuma-map-bind)))
  (println (exec-time-mean (cuma-partial)))
  (println (exec-time-mean (cuma-let)))
  (println (exec-time-mean (cuma-layout)))
  )

(defn print-all-execution-mean-time
  []
  (print-cuma-execution-mean-time)

  (println "## clostache")
  (println (exec-time-mean (clostache-unescaped)))
  (println (exec-time-mean (clostache-escaped)))
  (println (exec-time-mean (clostache-dotted)))
  (println (exec-time-mean (clostache-if)))
  (println (exec-time-mean (clostache-for)))
  (println (exec-time-mean (clostache-map-bind)))
  (println (exec-time-mean (clostache-partial)))

  (println "## selmer")
  (println (exec-time-mean (selmer-unescaped)))
  (println (exec-time-mean (selmer-escaped)))
  (println (exec-time-mean (selmer-dotted)))
  (println (exec-time-mean (selmer-if)))
  (println (exec-time-mean (selmer-for)))
  (println "") ; map-bind
  (println "") ; partial
  (println "") ; let
  (println (exec-time-mean (selmer-layout)))
  )

(defn -main
  [& [full-bench]]
  (binding [*full-bench* (some? full-bench)]
    (print-all-execution-mean-time)))
