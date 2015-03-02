(ns akiee.app-db
  (:require [akiee.fileoperations :as fo]
            [akiee.datadefinitions :as dd :refer [global-state]]
            [akiee.constants :refer [TODO DOING DONE ALL]]
            [cljs.test :refer-macros [is deftest]]
            [cljs.nodejs :as nj]
            [reagent.core :as rc]))

;; Node modules
(def org (nj/require "./lib/markdown-org-mode-parser"))
(def parse-file (.-parseBigString org))

(enable-console-print!)

;; app state operations for Akiee

;; =================
;; Constants:

(def null (js* "null"))
(def FP (fo/create-task-list-file (fo/user-home)))

;;(defrecord global-state [editor? search? entry? ls lon])

;; =================
;; Functions:

(defn node=?
  "Node Node -> Boolean
  Compares 2 Nodes n1 n2, the :key of the nodes is ignored,
  because it's random"
  [n1 n2]
  (and
   (= (:level n1)      (:level n2))
   (= (:headline n1)   (:headline n2))
   (= (:body n1)       (:body n2))
   (= (:tag n1)        (:tag n2))
   (= (:tags n1)       (:tags n2))
   (= (:todo n1)       (:todo n2))
   (= (:priority n1)   (:priority n2))
   (= (:scheduled n1)  (:scheduled n2))
   (= (:deadline n1)   (:deadline n2))
   (= (:properties n1) (:properties n2))
   (= (:drawer n1)     (:drawer n2))
   (= (:rank n1)       (:rank n2))
   (= (:style n1)      (:style n2))))

(is (= (node=? dd/N1 dd/N1a) true))
(is (= (node=? dd/N1 dd/N2)  false))

(defn jsnode->node
  "JsNode -> Node
  consumes a javascript org-node (object) jn and produces a node
  TODO make this more robust and general"
  [jn]
  {:key (aget jn "key")
   :level (aget jn "level")
   :headline (str (aget jn "headline"))
   :body (str (aget jn "body"))
   :tag nil
   :tags {}
   :todo (aget jn "todo")
   :priority nil
   :scheduled nil
   :deadline nil
   :properties {}
   :drawer {}
   :rank (aget jn "rank")
   :style nil})

(is (node=? (jsnode->node dd/jsN1) dd/N1))
(is (= (:key (jsnode->node dd/jsN1)) (:key dd/N1)))

(defn array->vec
  "JsArray Vector -> Vector
  consumes a javascript array a and Vector v and produces a vector
  TODO make this more robust and general"
  [v a]
  (if (= (.-length a) 0)
    v
    (array->vec
     (conj v (.shift a))
     a)))

(is (= (array->vec [] (js* "[]")) []))
(is (= (array->vec [] (js* "[1, 2, 3]")) [1 2 3]))


(defn nodes
  "String -> ListOfNodes
  consumes the path p to the task file and produces a list of nodes
  TODO find way to test, without :key"
  [p]
  (let [nodes-array (parse-file (fo/load-file p))]
    (map jsnode->node (array->vec [] nodes-array))))


(defn load-app-state
  "String -> GS
  consumes the path p to the task file and produces the initial app-state
  TODO find way to test, without :key"
  [p]
  (global-state. false false false DOING (nodes p)))

(is (= (:lon (load-app-state fo/testfile) [{:key "orgode_33.##" :level 1 :headline "Inbox"
                                            :body "" :tag nil :tags {}  :todo "DOING"
                                            :priority nil :scheduled nil :deadline nil
                                            :properties {} :drawer {} :rank nil  :style nil}
                                           {:key "orgode_33.##" :level 2  :headline "Test"
                                            :body ""  :tag nil :tags {} :todo "TODO"
                                            :priority nil :scheduled nil :deadline nil
                                            :properties {} :drawer {} :rank nil :style nil}])))


(def app-state  (rc/atom (load-app-state FP)))
(def test-state (rc/atom (load-app-state fo/testfile)))

(println @app-state)

(defn tasks
  "nil -> lon"
  []
    (:lon @app-state))

;; old app-state :
#_
{:tasks
    [{:todo "DOING" :headline "Remove Ace-dependency from enterTask.js"}
     {:todo "DOING" :headline "AuxMoney Test starten"}
     {:todo "DOING" :headline "Karo und Diana das Briefing für das Designn schicken"}
     {:todo "DOING" :headline "Licht reklamieren, Kontoauszug raussuchen"}
     {:todo "DOING" :headline "Bräter 4 Stunden toasten"}
     {:todo "TODE" :headline "Ich teile nicht! schreiben"}
     {:todo "DONE" :headline "Verzeichnis-akiee von Grund auf euida, mit leinigen templates"}]}
