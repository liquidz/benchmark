(ns benchmark.core
  (:require
    [cuma.core      :as cuma]
    [criterium.core :as crit]
    [clojure.string :as str]))

(def ^:dynamic *full-bench* false)
(def mode-printed? (atom false))

(defn print-mode
  []
  (when-not @mode-printed?
    (reset! mode-printed? true)
    (let [s (if *full-bench* "NORMAL" "QUICK")]
      (println
        (cuma/render "*** $(mode) MODE ***\n" {:mode s})))))

(defmacro exec-time-mean
  [expr]
  `(binding [crit/*final-gc-problem-threshold* 10] ; avoid GC WARNING
     (let [opt#     {:supress-jvm-option-warnings true}
           results# (if *full-bench*
                      (crit/benchmark ~expr opt#)
                      (crit/quick-benchmark ~expr opt#))
          mean#     (-> results# :mean first)
          [f# u#]   (crit/scale-time mean#)]
      (crit/format-value mean# f# u#))))

(defn -main [])
