(defproject accounting-period-close "0.1.0-SNAPSHOT"
  :description "Accounting Period Close client"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"] [http-kit "2.1.19"][org.clojure/data.json "0.2.6"]]
  :main ^:skip-aot accounting-period-close.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
