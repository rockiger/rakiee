(ns akiee.datadefinitions)
;; =================
;; Data definitions:

;; TaskState is one of:
;; - "TODO"
;; - "DOING"
;; - "DONE"
;; interp. as the current state of a task

#_
(defn fn-for-taskstate [ts]
  (cond
   (= ts TODO)  (true)
   (= ts DOING) (true)
   (= ts DONE)  (true)))


;; Node is a Object/map
;; interp. as a Task with properties:
;; - key: unique key in markdown file
;; - level: level in markdown structure
;; - headline
;; - body
;; - tag: default null
;; - tags default {}
;; - todo: task state, default null
;; - priority, default null
;; - scheduled, default null
;; - deadline, default null
;; - drawer, default {}
;; - rank: unique Ranking in File, default null

(def n1 (clj->js {
         :key "orgode_33.##"
         :level 2
         :headline "As a user I want to change the state of a task with a simple action."
         :body ""
         :tag nil
         :tags {}
         :todo "DOING"
         :priority nil
         :scheduled nil
         :deadline nil
         :properties {}
         :drawer {}
         :rank nil}))

#_
(defn fn-for-node [n]
  (... (:todo n)
       (:headline n)
       (:rank n)))

;; Rank is one of:
;; - Integer [1, ...]
;; - null
;; interp. as the ranking of a task
;; should be an int > 0
;; nill equals infinity
;; should be atomar

#_
(defn fn-for-rank [r]
  (if (nil? r)
    (... r)
    (... r)))
