(ns akiee.handlers
  (:require [goog.events :as events]
            [akiee.app-db :as db]
            [akiee.node :as no]
            [akiee.dom-helpers :as dom :refer [get-element]]
            [akiee.fileoperations :as fo]
            [akiee.rank :as r]))
;; Nodejs modules
(def gui (js/require "nw.gui"))

(enable-console-print!)
;; Handles events for user interactions

;; =================
;; Constants:
(def WIN (.get (.-Window gui)))

;; =================
;; Functions:

(defn cancel-enter-task
  "closes the entry box and hides it"
  []
  (let [hdln (get-element "enter-headline")]
    (do
      (set! (.-value hdln) "")
      (db/switch-entry!))))

(defn cancel-search
  "closes the search box and resets the search-string in the app-state"
  []
  (db/switch-search!))

(defn handle-enter-task
  "DOMElement -> Bool
  Handles the submisson of element e that are created by the enter task form"
  [ev]
  (let [form (aget ev "target")
        els  (aget form "elements")
        hdln (.-value (aget els "headline"))
        tast (.-value (aget els "task-status"))
        tapr (.-value (aget els "task-project"))]
    (do
      (when (not= hdln "")
        (db/add-task! tast hdln tapr))
      (cancel-enter-task)
      false)))

(defn handle-keyup
  "KeyEvent -> GlobalState
  Handles the keyevents that are created by js/document"
  [ev]
  (let [ky  #(.-keyCode %)
        ctrl? #(.-ctrlKey %)]
    (cond
     (and (= (ky ev) 32) (ctrl? ev)) (db/switch-editor!)                      ;; Ctrl + Space
     (and (or (= (ky ev) 49) (= (ky ev) 97)) (ctrl? ev)) (db/switch-todo!)    ;; Ctrl + 1
     (and (or (= (ky ev) 50) (= (ky ev) 98)) (ctrl? ev)) (db/switch-doing!)   ;; Ctrl + 2
     (and (or (= (ky ev) 51) (= (ky ev) 99)) (ctrl? ev)) (db/switch-done!)    ;; Ctrl + 3
     (and (or (= (ky ev) 52) (= (ky ev) 100)) (ctrl? ev)) (db/switch-all!)    ;; Ctrl + 4
     (and (or (= (ky ev) 69) (= (ky ev) 101)) (ctrl? ev)) (db/switch-editor!) ;; Ctrl + E
     (and (= (ky ev) 13) (ctrl? ev)) (db/switch-entry!)                       ;; Ctrl + Enter
     (and (= (ky ev) 70) (ctrl? ev)) (db/switch-search!)                      ;; Ctrl + F
     (and (= (ky ev) 27) (db/entry?)) (cancel-enter-task)                     ;; ESC - entry?
     (and (= (ky ev) 27) (db/search?)) (cancel-search))))                     ;; ESC - search?

(defn register-keyevents
  "Register the keyhandlers"
  []
  (events/listen js/document "keyup" handle-keyup))

(defn handle-close
  "Event ->
  Handles the close event of win"
  [ev]
  (do
    (fo/save-task-file (no/lon->md (db/nodes)) "/home/macco/.akiee/testflow.md" (db/changed?) db/set-changed!)
    (.close WIN true)))

(defn handle-blur
  "Event ->
  Handles the close event of win"
  [ev]
  (do
    (fo/save-task-file (no/lon->md (db/nodes)) "/home/macco/.akiee/testflow.md" (db/changed?) db/set-changed!)))

(defn register-winevents
  "Register the window event handlers"
  []
  (do
    (events/listen js/window "blur" handle-blur)
    (.on WIN "close" handle-close))) ;; can't use google closure here, because of nw.js

(defn handle-onchange-search
  "Event -> GlobalState
  Consumes the onchange Event ev and changes global filter string for search;
  returns the app-state"
  [ev]
  (db/set-search-string! (.-value (.-target ev)))); set-search-string!

(defn handle-blur-editor
  "Event -> GlobalState
  Consumes the onblur Event ev and changes global lon ;
  returns the app-state"
  [ev]
  (let [lon (map no/jsnode->node (no/array->vec [] (no/parse-file (.-value (.-target ev)))))]
    (do
      (db/reset-lon! db/app-state lon)
      (db/set-changed! true))))

(defn handle-onclick-taskstate
  "Event -> GlobalState
  Consumes the onclick Event ev and changes global lon with task to next state;
  returns the app-state"
  [ev]
  (let [row  (.-parentNode (.-currentTarget ev))
        ky (.-key (.-dataset row))]
    (db/next-ts! ky)))

(defn rank-helper
  "Event -> String
  Consume an Event from a rank changing button and returns the key of the corresponding row"
  [ev]
  (.-key (.-dataset (.-parentNode (.-parentNode (.-currentTarget ev))))))

(defn handle-onclick-down
  "Event -> GlobalState
  Consumes the onclick Event ev and changes the global lon with rank change down-wards"
  [ev]
  (let [ky (rank-helper ev)]
    (r/down-rank ky)))

(defn handle-onclick-up
  "Event -> GlobalState
  Consumes the onclick Event ev and changes the global lon with rank change up-wards"
  [ev]
  (let [ky (rank-helper ev)]
    (r/up-rank ky)))
