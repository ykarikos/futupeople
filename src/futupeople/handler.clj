(ns futupeople.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (GET "/" [] (str "Hello Futurice on " (java.util.Date.)))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
