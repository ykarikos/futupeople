(ns futupeople.data
  (:require [clj-http.client :as client]))

(def url "https://reports.app.futurice.com/futuqu/rada/people")

(defn get-people-data [auth-cookie]
  (let [response (client/get url
                   {:cookies auth-cookie})]
    {:headers (:headers response)
     :body (:body response)}))
