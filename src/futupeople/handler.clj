(ns futupeople.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.cookies :refer [wrap-cookies]]
            [futupeople.people :refer [get-summary]]
            [ring.util.codec :refer [url-encode]]))

(def auth-cookie-name "auth_pubtkt")

(defn- get-auth-cookie [request]
  (let [cookie (-> request
                   :cookies
                   (get auth-cookie-name))]
    (when cookie
      {auth-cookie-name {:value (-> cookie :value url-encode)}})))

(defn- get-people [request]
  (let [auth-cookie (get-auth-cookie request)]
    (if (nil? auth-cookie)
      (route/not-found "Auth cookie missing")
      (get-summary auth-cookie))))

(defroutes app-routes
  (GET "/" req (get-people req))
  (route/not-found "Not Found"))

(def app
  (-> app-routes
    (wrap-resource "public")
    (wrap-defaults site-defaults)
    (wrap-cookies)))
