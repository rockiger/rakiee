(ns akiee.fileoperations
  (:require [cljs.test :refer-macros [is deftest]]
            [cljs.nodejs :as nj]))
(enable-console-print!)


;; Node modules
(def fs (js/require "fs"))
(def path (nj/require "path"))
(def process nj/process)
(def __dirname (js* "global.__dirname"))

(println "Current Working direktory")
(println (.cwd process))
(println "Directory of file")
(println __dirname)


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

(is (string? (task-file-path)))
