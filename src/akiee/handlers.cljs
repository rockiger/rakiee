(ns akiee.handlers
  (:require [goog.events :as events]
            [akiee.app-db :as db]))

(enable-console-print!)
;; Handles events for user interactions

;; =================
;; Functions:

(defn handle-keyup [ev]
  "KeyEvent -> GlobalState
  Handles the keyevents that are created by js/document"
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
     (and (= (ky ev) 70) (ctrl? ev)) (db/switch-search!))))                   ;; Ctrl + F

(defn register-keyevents
  "Register the keyhandlers"
  []
  (events/listen js/document "keyup" handle-keyup))
