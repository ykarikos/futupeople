(defproject futupeople "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.2"]
                 [clj-http "3.9.1"]
                 [ring/ring-codec "1.1.1"]
                 [org.clojure/data.json "0.2.6"]
                 [environ "1.1.0"]]
  :plugins [[lein-ring "0.12.4"]]
  :ring {:handler futupeople.handler/app}
  :uberjar-name "futupeople.jar"
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]}})
