(ns futupeople.people
  (:require [futupeople.data :refer [get-people-data]]
            [clojure.string :as s]
            [hiccup.core :refer [html]]
            [environ.core :refer [env]]))


(def lines
  [:ul {:class "lines"}
   [:li {:class "line l--0"}]
   [:li {:class "line l--10"}
    [:span {:class "line-label"} "10%"]]
   [:li {:class "line l--20"}
    [:span {:class "line-label"} "20%"]]
   [:li {:class "line l--30"}
    [:span {:class "line-label"} "30%"]]
   [:li {:class "line l--40"}
    [:span {:class "line-label"} "40%"]]
   [:li {:class "line l--50"}
    [:span {:class "line-label"} "50%"]]])


(defn- chart [title data sum]
  [:div {:class "chart"}
   [:h2 title]
   [:ul {:class "chart-horiz"}
    (for [key (-> data keys sort)]
      (let [value (get data key)
            key-title (s/replace key " " "&nbsp;")
            p (->> sum float (/ value) (* 170) int)]
        [:li {:class "chart-bar" :style (str "width: " p "%")}
         [:span {:class "chart-label"}
          key-title ":&nbsp;" value]]))]])

(defn get-summary [auth-cookie]
  (let [people-data (get-people-data auth-cookie)]
    (html [:html
           [:head
            [:link {:rel "stylesheet"
                    :href "/style.css"
                    :type "text/css"
                    :media "all"}]]
           [:body
            [:h1 "Futupeople"]
            [:ul
             [:li "Number of active people: "
              (:count people-data)]
             [:li "Number of supervisors: "
              (:supervisors-count people-data)]
             [:li "Number of expats: "
              (:expat-count people-data)]]
            [:div {:class "top"}
             lines
             [:div {:class "charts"}
              (chart "Offices" (:offices people-data) (:count people-data))
              (chart "Tribes" (:tribes people-data) (:count people-data))
              (chart "Roles" (:roles people-data) (:count people-data))
              (chart "Starting year" (:hire-years people-data) (:count people-data))]]
            [:h2 {:style "padding-top: 90px;"}
             "Supervisors' team sizes"]
            [:ul
             [:li "Minimum (number of supervisees): " (-> people-data :supervisees :min)]
             [:li "Maximum: " (-> people-data :supervisees :max)]
             [:li "Median: " (-> people-data :supervisees :median)]]
            [:h2 "Contract types"]
            [:ul
             (for [contract (:contract-types people-data)]
               [:li (key contract) ": " (val contract)])]
            [:hr]
            [:p "Data Source: "
             [:a {:href (env :people-list)} "Personio"]]
            [:p
             "Source code: "
             [:a {:href "https://github.com/ykarikos/futupeople"} "https://github.com/ykarikos/futupeople"]]]])))
