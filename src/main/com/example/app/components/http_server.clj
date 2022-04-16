(ns com.example.app.components.http-server
  (:require [aleph.http :as http]
            [taoensso.timbre :as log]
            [clojure.string :refer [starts-with?]]
            [crypticbutter.snoop :refer [>defn =>]]))

(defn handler [req]
  {:status 200
   :headers {"content-type" "text/plain"}
   :body "hello!"})

(def AlephServer
  [:fn #(-> % str (starts-with? "AlephServer"))])

(def HttpServerConfig
  [:map
   [:config [:map
             [:http-server
              [:map
               [:port :int]]]]]])

(>defn
 start
 [{:keys [config]}]
 [HttpServerConfig => AlephServer]
 (let [{:keys [http-server]} config]
   (log/info "Starting http-server with " http-server)
   (http/start-server handler http-server)))

(>defn
 stop
 [server]
 [AlephServer => :any]
 (.close server))
