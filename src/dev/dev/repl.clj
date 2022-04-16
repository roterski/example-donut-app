(ns dev.repl
  (:require [com.example.app.system :as system]
            [donut.system :as ds]
            [donut.system.repl :as dsr]
            [portal.api :as p]))

(defmethod ds/named-system :dev
  [_]
  (ds/system :base {[:env :name] :dev}))

(defmethod ds/named-system ::ds/repl
  [_]
  (ds/system :dev))

(defn start []
  (system/start (ds/named-system ::ds/repl)))

(defn stop []
  (system/stop))

(defn restart []
  (dsr/restart))


(comment
  (do
    (do
      (def portal (p/open {:launcher :vs-code}))
      (add-tap #'p/submit))
    (start)
    )
  (restart)
  (stop)
  *e ;; last error

  (tap> :hello) ;; put value to portal
  @dev.repl/portal ;; get value selected in portal

  )
