(ns akiee.core
  (:require [clojure.browser.repl :as repl]
            [clojure.string :as string]
            [reagent.core :as r]
            [akiee.constants :as c]
            [akiee.app-db :refer [app-state tasks]]))

(enable-console-print!)

;; TODO unit test for reagent
;; TODO figwhell for node-webkit

;; =================
;; Constants:

(def TODO "TODO")
(def DONE "DONE")
(def DOING "DOING")
(def ALL "ALL")

;; =================
;; Data definitions:

;; World State is ... (give WS a better name)

#_
(def app-state
  (r/atom
   {:tasks
    [{:todo "DOING" :headline "Remove Ace-dependency from enterTask.js"}
     {:todo "DOING" :headline "AuxMoney Test starten"}
     {:todo "DOING" :headline "Karo und Diana das Briefing für das Designn schicken"}
     {:todo "DOING" :headline "Licht reklamieren, Kontoauszug raussuchen"}
     {:todo "DOING" :headline "Bräter 4 Stunden toasten"}
     {:todo "TODE" :headline "Ich teile nicht! schreiben"}
     {:todo "DONE" :headline "Verzeichnis-akiee von Grund auf euida, mit leinigen templates"}]}))

;; =================
;; Functions:

(defn task [t]
  [:tr
   [:td (:todo t)]
   [:td (:headline t)]])

(defn task-list []
  [:table
   (for [t (tasks)]
     [task t])])

(defn big-bang []
  (r/render-component
    [task-list]
    (.getElementById js/document "root")))

(big-bang)
