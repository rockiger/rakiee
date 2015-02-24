(ns akiee.fileoperations
  (:require [cljs.test :refer-macros [is deftest]]))
(enable-console-print!)


;; Node modules
(def fs (js/require "fs"))
(def path (js/require "path"))


;; All file operations for Akiee

;; =================
;; Constants:


;; =================
;; Functions:

;; nil -> String

(defn task-file-path
  "nil -> String
  produce the path of the task file"
  []
  true)

(is (= (string? (task-file-path))))
