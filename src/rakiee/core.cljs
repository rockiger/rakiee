(ns rakiee.core
  (:require [clojure.browser.repl :as repl]
            [clojure.string :as string]
            [reagent.core :as r]))

;; TODO Browser repl in node-webkit (repl/connect "http://localhost:9000/repl")
(enable-console-print!)

(println "Hello world!")

(def app-state
  (r/atom
   {:tasks
    [{:todo "DOING" :headline "Remove Ace-dependency from enterTask.js"}
     {:todo "DOING" :headline "AuxMoney Test starten"}
     {:todo "DOING" :headline "Karo und Diana das Briefing für das Designn schicken"}
     {:todo "DOING" :headline "Licht reklamieren, Kontoauszug raussuchen"}
     {:todo "DOING" :headline "Bräter 4 Stunden toasten"}
     {:todo "DOING" :headline "Ich teile nicht! schreiben"}
     {:todo "DOING" :headline "Verzeichnis-rakiee von Grund auf aufbauen, ohne leinigen templates"}]}))

(defn task [t]
  [:tr
   [:td (:todo t)]
   [:td (:headline t)]])

(defn task-list []
  [:table
   (for [t (:tasks @app-state)]
     [task t])])

(defn start []
  (r/render-component
    [task-list]
    (.getElementById js/document "root")))

(start)