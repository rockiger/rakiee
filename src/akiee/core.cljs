(ns akiee.core
  (:require [clojure.browser.repl :as repl]
            [clojure.string :as string]
            [reagent.core :as r]
            [akiee.constants :as c]
            [akiee.app-db :as db]))

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
  Consumes the icon name in, the id and title t of the button, the test function tfn?;
  produces the component for the button."
  [in id t tfn? onfn]
  (let [icon-name (str "fa-" in)
        active? (if (tfn?) "active" "")]
  [:button.btn.btn-default.navbar-btn.btn-square {:id id :title t :class active? :on-click onfn}
   [:span.fa.fa-fw {:class icon-name}]]))

(def editor-switch [switch-button "file-text-o" "show-editor" "Ctrl+E / Ctrl+Space" db/editor? db/switch-editor!])
(def search-switch [switch-button "search" "show-searchbox" "Ctrl+F" db/search? db/switch-search!])
(def entry-switch  [switch-button "plus" "show-enter-task" "Ctrl+Enter" db/entry? db/switch-entry!])

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
     editor-switch
     search-switch
     entry-switch]]])


(defn select
  "ListOf* -> Component
  Consumes a list of anything loa; produces the component of a select field."
  [loa]
  [:select#enter-task-status.form-control
   (for [a loa]
     [:option a])])

(defn enter-task-status
  "ListOfTaskState -> Component
  Consumes the a list of taskstate lot; 
  produces the component of the select field for the state of the new task."
  [lot]
  (select lot))

(defn enter-task-project
  "ListofString -> Component
  Consumes a list of string los; produces the component for the project select."
  [los]
  (select los))

(defn enter-task
  "-> Component
  The entry form for entering tasks"
  []
  (let [show? (if (db/entry?)
                {:style {:display "block"}}
                {:style {:display "none"}})]
  [:div#enter-task-div.container-fluid show?
   [:form#enter-task.row
    [:input#enter-headline.form-control {:type "text" :placeholder "Enter Headline"}]
    [enter-task-status ["TODO", "DOING", "DONE"]]
    [enter-task-project ["Inbox"]]
    [:button.btn.btn-default {:type "submit"} "Create"]
    [:button#cancel-enter-task.btn.btn-link {:type "button"} "Cancel"]]]))

(defn search
  "-> Component
  The entry form for searching tasks"
  []
  (let [show? (if (db/search?)
                {:style {:display "flex"}}
                {:style {:display "none"}})]
  [:div#search-form show?
   [:input#search-input.form-control {:type "text"}]
   [:span#search-input-icon.fa.fa-search]]))

(defn editor
  "-> Component
  The textarea to directly edit the task list in markdown"
  []
  (let [show? (if (db/editor?)
                {:style {:display "inline-block"}}
                {:style {:display "none"}})]
  [:div#editor show?
   [:textarea {:rows 3}]]))

(defn task [t]
  [:tr
   [:td (:todo t)]
   [:td (:headline t)]])

(defn task-list []
  (let [show? (if (not (db/editor?))
                {:style {:display "inline-block"}}
                {:style {:display "none"}})]
  [:table {:display show?}
   (for [t (db/tasks)]
     [task t])]))

(defn app
  " -> Component
  Produces the base comment"
  []
  [:div#app
   [toolbar]
   [enter-task]
   [search]
   [editor]
   [task-list]])

(defn big-bang []
  (r/render-component
    [app]
    (.getElementById js/document "root")))

(big-bang)

(db/switch-entry!)
(db/switch-entry!)
