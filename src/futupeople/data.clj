(ns futupeople.data
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]
            [environ.core :refer [env]]))

(def url (env :people-list))

; TODO
; Caching

(defn- get-frequencies
  "Get frequencies from `people` with keyword `key` (mapped with `f`)"
  ([people key]
   (->> people
        (map key)
        frequencies))
  ([people key f]
   (->> people
        (map key)
        (map f)
        frequencies)))

(defn median [numbers]
  (nth (sort numbers) (/ (count numbers) 2)))

(defn- parse-data [response]
  (let [data (-> response
               :body
               (json/read-str :key-fn keyword))
        active (->> data
                    (filter #(= "internal" (:employmentType %)))
                    (filter #(= "Active" (:status %))))
        supervisors (->> active (map :supervisorId) frequencies)
        supervisees (map val supervisors)]
    {:count (count active)
     :hire-years (get-frequencies active :hireDate #(subs % 0 4))
     :offices (get-frequencies active :office)
     :tribes (get-frequencies active :tribe)
     :roles (get-frequencies active :role)
     :supervisors-count (count supervisors)
     :expat-count (->> active (filter :expat) count)
     :supervisees {:median (median supervisees)
                   :min (reduce min supervisees)
                   :max (reduce max supervisees)}
     :contract-types (->> active (map :contractType) frequencies)}))

(defn- content-type [response]
  (-> response
      :headers
      (get "Content-Type")))

(defn- ok-response? [response]
  (and (= 200 (:status response))
       (-> response
           content-type
           (.startsWith "application/json"))))

(defn get-people-data [auth-cookie]
  (let [response (client/get url
                   {:cookies auth-cookie})]
    (if (ok-response? response)
      (parse-data response)
      (do
        (println "Response not good:" (:status response) (content-type response))
        {}))))
