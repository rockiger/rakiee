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

;TODO
(defn list-state-button
  "String String String -> Component
  Consumes the text tx, the id and the title t of the button;
  produces the component for the button."
  [tx id t]
  [:button.btn.btn-default.navbar-btn.btn-state {:type "button" :id id :title t} tx])

(defn switch-button
  "String String String -> Component
  Consumes the icon name in, the id and title t of the button;
  produces the component for the button."
  [in id t]
  (let [icon-name (str "fa-" in)]
  [:button.btn.btn-default.navbar-btn.btn-square {:id id :title t}
   [:span.fa {:class icon-name}]]))

(defn toolbar
  "-> Component
  The toolbar for changing the state of the Akiee"
  []
  [:nav#toolbar.navbar.navbar-default.navbar-fixed-top {:role "navigation"}
   [:div.container-fluid
    [:div.navbar-flex
     [:div#taskbuttons.btn-group
      [list-state-button "Todo" "show-todo" "Ctrl+1"]
      [list-state-button "Doing" "show-doing" "Ctrl+2 / Ctrl+Space"]
      [list-state-button "Done" "show-done" "Ctrl+3"]]
     [list-state-button "Board" "show-all" "Ctrl+4"]
     [:div.spacer]
     [switch-button "file-text-o" "show-editor" "Ctrl+E / Ctrl+Space"]
     [switch-button "search" "show-searchbox" "Ctrl+F"]
     [switch-button "plus" "show-enter-task" "Ctrl+Enter"]]]])

(defn task [t]
  [:tr
   [:td (:todo t)]
   [:td (:headline t)]])

(defn task-list []
  [:table
   (for [t (tasks)]
     [task t])])


(defn app
  " -> Component
  Produces the base comment"
  []
  [:div#app
   [toolbar]
   [task-list]])

(defn big-bang []
  (r/render-component
    [app]
    (.getElementById js/document "root")))

(big-bang)
