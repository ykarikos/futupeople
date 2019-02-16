(ns futupeople.people
  (:require [futupeople.data :refer [get-people-data]]))

(defn get-summary [auth-cookie]
  (let [people-data (get-people-data auth-cookie)]
    people-data))
