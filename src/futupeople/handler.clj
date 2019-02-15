(ns futupeople.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.cookies :refer [wrap-cookies]]))

(defn- get-cookie [request]
  (-> request
      :cookies
      (get "auth_pubtkt")
      :value))

(defroutes app-routes
  (GET "/" req (str "Hello Futurice on " (java.util.Date.)
                     " '" (get-cookie req) "'"))
  (route/not-found "Not Found"))

(def app
  (-> app-routes
    (wrap-defaults site-defaults)
    (wrap-cookies)))
