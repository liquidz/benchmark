(ns benchmark.core
  (:require
    [criterium.core :as crit]
    [clojure.string :as str]))

(def ^:dynamic *full-bench* false)

(defmacro exec-time-mean
  [expr]
  `(binding [crit/*final-gc-problem-threshold* 10] ; avoid GC WARNING
     (let [opt# {:supress-jvm-option-warnings true}
           res#            (if *full-bench*
                             (crit/benchmark       ~expr opt#)
                             (crit/quick-benchmark ~expr opt#))
           mean#           (-> res# :mean first)
           [factor# unit#] (crit/scale-time mean#)]
       (* mean# factor#))))
