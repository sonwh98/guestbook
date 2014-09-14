(ns guestbook.db)

(require '[datomic.api :as datomic])
(def uri "datomic:mem://databank")

(defn db-exists? [uri] (not (datomic/create-database uri)))
(defn get-connection [] (datominc/connect uri))
(defn get-db [] (datomic/db (get-connection)))
(defn do-transact [data-struct] @(datomic/transact (get-connection) data-struct))

(defn import-test-data []
  (if-not (db-exists?)
    (let [schema (read-string (slurp ("resources/guestbook-schema.edn")))]
      (do-transact schema))))