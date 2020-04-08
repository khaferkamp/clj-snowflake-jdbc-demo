(ns khaferkamp.snowflake-jdbc-demo
  (:require [next.jdbc :as jdbc]
            [cprop.core :refer [load-config]]
            [cprop.source :as source]
            [clojure.pprint :refer [pprint]])
  (:gen-class))

(defn -main
  "Example usage of jdbc with Snowflake elastic cloud DWH in clojure"
  [& args]
  (let [cfg (load-config :merge [(source/from-props-file "resources/db/snowflake.properties")])
        jdbcUrl  (str "jdbc:snowflake://" (->> cfg :snowflake :tenant))
        ds (jdbc/get-datasource {:jdbcUrl jdbcUrl
                                 :classname "net.snowflake.client.jdbc.SnowflakeDriver"
                                 :user (->> cfg :snowflake :user)
                                 :password (->> cfg :snowflake :password)})]
    (println "Listing all databases...")
    (pprint (jdbc/execute! ds ["SHOW DATABASES"]))))
