(ns akiee.datadefinitions)

;; helper function for templates
(defn ddd [& args]
  true)

(enable-console-print!)

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
;; - style: the NodeStyle of a node TODO: has to be initially creted in markdown-org-mode;
;;          defaul null

(def N1 {:key "orgode_33.##"
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
         :rank nil
         :style nil})

#_
(defn fn-for-node [n]
  (ddd (:todo n)
       (:headline n)
       (:rank n)))

;; Rank is one of:
;; - Integer [1, ddd]
;; - null
;; interp. as the ranking of a task
;; should be an int > 0
;; nill equals infinity
;; should be atomar

#_
(defn fn-for-rank [r]
  (if (nil? r)
    (ddd r)
    (ddd r)))

;; ListOf(Node) is on of:
;; - []
;; - (conj ListOfNode Node)
;; interp. a list of Nodes

(def LON-1 '())
(def LON-2 (cons N1 '()))

#_
(defn fn-for-lon [lon]
  (cond ((empty? lon) true)
        :else (ddd (first lon)
                   (fn-for-los (rest lon)))))


;; Rank is one of:
;; - Integer [1, ddd]
;; - nil
;; interp. as the ranking of a task
;; should be an int > 0

(def RANK-1 10)
(def RANK-2 nil)

#_
(defn fn-for-rank [rank]
  (if (= rank 0)
    (ddd rank)
    (ddd rank)))


;; Liststate is one of:
;; - TaskState
;; - "ALL"
;; - false ; don't know if neccessary
;; interp. what is shown in the tasklist
;; ALL means all TaskState tasks are shown combined


(def LS-1 "TODO")
(def LS-2 "ALL")
(def LS-3 "DOING")

SocialImpactWLAN
#_
(defn fn-for-liskstate [ls]
  (cond
   (= ls TODO)  (ddd)
   (= ls DOING) (ddd)
   (= ls DONE)  (ddd)
   :else        (ddd)))


;; NodeStyle is one of:
;; - TaskState
;; - nil
;; interp. the styling of a Node

(def NS-1 "TODO")
(def NS-2 nil)

#_
(defn fn-for-nodestyle [ns]
  (cond
   (= ns TODO)  (ddd)
   (= ns DOING) (ddd)
   (= ns DONE)  (ddd)
   :else        (ddd)))


;; SwitchState is one of:
;; -true
;; -false
;; interp. if a function is active or NodeStyle

(def editor-switch false)
(def search-switch true)

#_
(defn fn-for-switchstate [ss]
  (if ss
    (ddd ss)
    (ddd ss)))

(defrecord global-state [editor? search? entry? ls lon])
;; GlobalState is (global-state. false false false LS-3 LON-2) of:
;; - SwitchState editor
;; - SwitchState search
;; - SwitchState entry
;; - ListState
;; - ListOfNode

(def GS1 (global-state. false false false LS-3 LON-2))
(def GS2 (global-state. false false false LS-3 (cons N1 LON-2)))

(println GS2)

(defn fn-for-game [gs]
  (ddd (fn-for-switchstate (:editor? gs))
       (fn-for-switchstate (:search? gs))
       (fn-for-switchstate (:entry? gs))
       (fn-for-liskstate (:ls gs))
       (fn-for-lon (:lon gs))))
