(ns com.example.app.system
  (:require [donut.system :as ds]
            [com.example.app.components.http-server :as http-server]
            [com.example.app.components.config :as config]))

(defonce system (atom nil))

(def base-system
  {::ds/defs
   {:app {:config      {:start (fn [conf _ _]
                                 (config/start conf))
                        :conf {:env (ds/ref [:env :name])}}
          :http-server {:start (fn [conf _ _]
                                 (http-server/start conf))
                        :stop  (fn [_ instance _]
                                 (http-server/stop instance))
                        :conf  {:config (ds/ref :config)}}}}})

(defmethod ds/named-system :prod
  [_]
  (ds/system :base {[:env :name] :prod}))

(defmethod ds/named-system :base
  [_]
  base-system)

(defn start [s]
  (reset! system (ds/start s))
  ::start)

(defn stop []
  (ds/stop @system)
  ::stop)

(comment
  (start (ds/named-system :prod))
  (stop)
  )
