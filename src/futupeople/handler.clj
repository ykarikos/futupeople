(ns futupeople.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.cookies :refer [wrap-cookies]]
            [clj-http.client :as client]))

(defn- get-auth-cookie [request]
  (-> request
      :cookies
      (get "auth_pubtkt")))

(def url "https://reports.app.futurice.com/futuqu/rada/people")

(defn- get-people [request]
  (let [auth-cookie (get-auth-cookie request)]
    (if (nil? auth-cookie)
      (route/not-found "Cookie missing")
      (let [response (client/get url
                       {:cookies {"auth_pubtkt" auth-cookie}})]
        {:headers (:headers response)
         :body (:body response)}))))

(defroutes app-routes
  (GET "/" req (str "Hello Futurice on " (java.util.Date.)
                     " '" (get-auth-cookie req) "'"))
  (GET "/people" req (get-people req))
  (route/not-found "Not Found"))

(def app
  (-> app-routes
    (wrap-defaults site-defaults)
    (wrap-cookies)))
