(ns api-clj.core
  (:require [ring.adapter.jetty :as ring-jetty]
            [reitit.ring :as ring]
            [muuntaja.core :as m]
            [reitit.ring.middleware.muuntaja :as muuntaja])
  (:gen-class))

;Return a string on the Base URL
; Return list of uers...
; users => id, return a specific user-id
; users POST, post a user

(def users (atom {}))

(defn string-handler [_]{:status 200 :body "on the code again"})

(defn create-user [{user :body-params}](let [id (str (java.util.UUID/randomUUID))
        users (->> (assoc user :id id)(swap! users assoc id))] {:status 200
                                                                :body users}))

(defn get-users [_] {:status 200 :body @users})

(defn get-user-by-id [{{:keys [id]} :path-params}] {:status 200 :body @users})

(defn get-user-by-id [{{:keys [id]} :path-params}]{:satus 200 
                                                   :body (get @users id)})

(def app(ring/ring-handler (ring/router ["/" ["users/id" get-user-by-id] ["user" {:get get-users
                                                                            :post create-user}]
                                   ["" string-handler]] 
                                  {:data {:muuntaja m/instance  
                                          :middleware [muuntaja/format-middleware]}})))

(defn start [] (ring-jetty/run-jetty app{:port 3000
                                         :join? false}))


(defn -main
  [& args]
  (start))