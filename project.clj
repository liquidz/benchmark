(defproject benchmark "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [criterium "0.4.3"]
                 [cuma "0.0.7"]
                 [de.ubercode.clostache/clostache "1.3.1"]]

  :aliases {"bench-cuma" ["run" "-m" "benchmark.cuma"]})
