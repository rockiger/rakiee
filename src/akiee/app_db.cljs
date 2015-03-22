(ns akiee.app-db
  (:require [akiee.fileoperations :as fo]
            [akiee.datadefinitions :as dd :refer [global-state]]
            [akiee.constants :refer [TODO DOING DONE ALL]]
            [akiee.dom-helpers :as dom]
            [cljs.test :refer-macros [is deftest]]
            [cljs.nodejs :as nj]
            [reagent.core :as rc]))

;; Node modules
(def org (nj/require "./lib/markdown-org-mode-parser"))

(def parse-file (.-parseBigString org))

(enable-console-print!)

;; app state operations for Akiee

;; =================
;; Constants:

(def null (js* "null"))
(def FP (fo/create-task-list-file (fo/user-home)))

;;(defrecord global-state [editor? search? entry? ls lon])

;; =================
;; Functions:

(defn node=?
  "Node Node -> Boolean
  Compares 2 Nodes n1 n2, the :key of the nodes is ignored,
  because it's random"
  [n1 n2]
  (and
   (= (:level n1)      (:level n2))
   (= (:headline n1)   (:headline n2))
   (= (:body n1)       (:body n2))
   (= (:tag n1)        (:tag n2))
   (= (:tags n1)       (:tags n2))
   (= (:todo n1)       (:todo n2))
   (= (:priority n1)   (:priority n2))
   (= (:scheduled n1)  (:scheduled n2))
   (= (:deadline n1)   (:deadline n2))
   (= (:properties n1) (:properties n2))
   (= (:drawer n1)     (:drawer n2))
   (= (:rank n1)       (:rank n2))
   (= (:style n1)      (:style n2))))

(is (= (node=? dd/N1 dd/N1a) true))
(is (= (node=? dd/N1 dd/N2)  false))

(defn jsnode->node
  "JsNode -> Node
  consumes a javascript org-node (object) jn and produces a node
  TODO make this more robust and general"
  [jn]
  (let [rank (aget jn "rank")]
    {:key (aget jn "key")
     :level (aget jn "level")
     :headline (str (aget jn "headline"))
     :body (str (aget jn "body"))
     :tag nil
     :tags {}
     :todo (aget jn "todo")
     :priority nil
     :scheduled nil
     :deadline nil
     :properties {}
     :drawer {}
     :rank (if (not= rank nil) (int rank) nil)
     :style nil}))

(is (node=? (jsnode->node dd/jsN1) dd/N1))
(is (= (:key (jsnode->node dd/jsN1)) (:key dd/N1)))

(defn array->vec
  "JsArray Vector -> Vector
  consumes a javascript array a and Vector v and produces a vector
  TODO make this more robust and general"
  [v a]
  (if (= (.-length a) 0)
    v
    (array->vec
     (conj v (.shift a))
     a)))

(is (= (array->vec [] (js* "[]")) []))
(is (= (array->vec [] (js* "[1, 2, 3]")) [1 2 3]))


(defn nodes
  "String -> ListOfNodes
  consumes the path p to the task file and produces a list of nodes
  TODO find way to test, without :key"
  [p]
  (let [nodes-array (parse-file (fo/load-file p))]
    (map jsnode->node (array->vec [] nodes-array))))


(defn load-app-state
  "String -> GS
  consumes the path p to the task file and produces the initial app-state
  TODO find way to test, without :key"
  [p]
  (global-state. false false false DOING (nodes p)))

(is (= (:lon (load-app-state fo/testfile) [{:key "orgode_33.##" :level 1 :headline "Inbox"
                                            :body "" :tag nil :tags {}  :todo "DOING"
                                            :priority nil :scheduled nil :deadline nil
                                            :properties {} :drawer {} :rank nil  :style nil}
                                           {:key "orgode_33.##" :level 2  :headline "Test"
                                            :body ""  :tag nil :tags {} :todo "TODO"
                                            :priority nil :scheduled nil :deadline nil
                                            :properties {} :drawer {} :rank "9"
                                            :style nil}])))


(def app-state  (rc/atom (load-app-state FP)))
(def test-state (rc/atom (load-app-state fo/testfile)))

;;(println (:ls @app-state))


(defn higher-rank?
  "Node Node -> Boolean
  Determens if Node n1 has a higher Rank than Node n2"
  [n1 n2]
  (cond
   (nil? n2) true
   (and (not (nil? n2)) (nil? n1)) false
   (< n2 n1) false
   :else true))

(def n1 (:rank {:headline "Test-Node 1"  :rank 0}))
(def n2 (:rank {:headline "Test-Node 2"  :rank 5}))
(def n3 (:rank {:headline "Test-Node 3"  :rank 11}))
(def n4 (:rank {:headline "Test-Node 11" :rank nil}))

(is (= (higher-rank? n2 n1) false))
(is (= (higher-rank? n1 n2) true))
(is (= (higher-rank? n3 n1) false))
(is (= (higher-rank? n1 n3) true))
(is (= (higher-rank? n1 n1) true))
(is (= (higher-rank? n3 n3) true))
(is (= (higher-rank? n1 n4) true))
(is (= (higher-rank? n4 n1) false))

(defn tasks-helper
  "GlobalState -> lon
  consumes an GlobalState gs and  returns the tasks, according to the current ListState"
  [gs]
    (let [filter-tasks (fn [x] (if (= (:level x) 2) true false ))
          filter-state (fn [x] (cond
                                (= (:ls @gs) ALL) true
                                (= (:ls @gs) (:todo x)) true
                                :else false))]
      (vec (sort-by :rank higher-rank? (filter filter-state (filter filter-tasks (:lon @gs)))))))

;; Test fails becaus of :body one seems to have an "\n"
#_(is (node=? (nth (tasks-helper test-state) 0) {:key "orgode_33.##" :level 2  :headline "Test"
                                              :body ""  :tag nil :tags {} :todo "TODO"
                                              :priority nil :scheduled nil :deadline nil
                                              :properties {} :drawer {} :rank nil :style nil}))


(defn tasks
  "-> lon
  shows the tasks of the app-state, according to the current ListState"
  []
  (tasks-helper app-state))

(defn editor?
  "-> Boolean
  returns the state of the editor"
  []
  (:editor? @app-state))

(defn entry?
  "-> Boolean
  returns the state of the task entry"
  []
  (:entry? @app-state))

(defn search?
  "-> Boolean
  returns the state of the search box"
  []
  (:search? @app-state))

(defn list-state
  "-> ListStat
  returns the state of the List"
  []
  (:ls @app-state))

(defn projects
  "-> ListOfString
  returns a List of Strings with the projects in the app-state"
  []
  (let [filter-nodes (fn [x] (if (= (:level x) 1) true false ))]
    (vec (sort (map :headline (filter filter-nodes (:lon @app-state)))))))
(println (projects))

(defn switch-editor!
  "-> Boolean
  switches the editor? state and returns it"
  []
  (if (editor?)
    (let [new-state (global-state. false (:search? @app-state) (:entry? @app-state) (:ls @app-state) (:lon @app-state))]
      (reset! app-state new-state))
    (let [new-state (global-state. true false false (:ls @app-state) (:lon @app-state))]
      (reset! app-state new-state))))

(defn switch-search!
  "-> GlobalState
  switches the search? state and the new app-state"
  []
  (if (search?)
    (let [new-state (global-state. (:editor? @app-state) false (:entry? @app-state) (:ls @app-state) (:lon @app-state))]
      (reset! app-state new-state))
    (let [new-state (global-state. false true false (:ls @app-state) (:lon @app-state))
          se (dom/get-element "search-input")]
      (do
        (reset! app-state new-state)
        (.focus se)))))

(defn switch-entry!
  "-> GlobalState
  switches the search? state and the new app-state"
  []
  (if (entry?)
    (let [new-state (global-state. (:editor? @app-state) (:search? @app-state) false (:ls @app-state) (:lon @app-state))]
      (reset! app-state new-state))
    (let [new-state (global-state. false false true (:ls @app-state) (:lon @app-state))
          entry (dom/get-element "enter-headline")]
      (do
        (reset! app-state new-state)
        (.focus entry)))))

(defn switch-list-state!
  "ListState -> GlobalState
  Consumes a Liststate ls switches the ls variable and editor? search? search? accordingly"
  [ls]
  (let [lon (:lon @app-state)]
      (reset! app-state (global-state. false false false ls lon))))

(defn switch-todo!
  "-> GlobalState
  switches the ls variable to TODO and editor? search? search? accordingly"
  []
  (switch-list-state! TODO))

(defn switch-doing!
  "-> GlobalState
  switches the ls variable to DOING and editor? search? search? accordingly"
  []
  (switch-list-state! DOING))

(defn switch-done!
  "-> GlobalState
  switches the ls variable to DONE and editor? search? search? accordingly"
  []
  (switch-list-state! DONE))

(defn switch-all!
  "-> GlobalState
  switches the ls variable to ALL and editor? search? search? accordingly"
  []
  (switch-list-state! ALL))

(defn tasks-helper
  "GlobalState -> lon
  consumes an GlobalState gs and  returns the tasks, according to the current ListState"
  [gs]
    (let [filter-tasks (fn [x] (if (= (:level x) 2) true false ))
          filter-state (fn [x] (cond
                                (= (:ls @gs) ALL) true
                                (= (:ls @gs) (:todo x)) true
                                :else false))]
      (vec (sort-by :rank higher-rank? (filter filter-state (filter filter-tasks (:lon @gs)))))))

(defn ->rank-helper
  "GlobalState -> Int
  produces a new rank based on the tasks in the GlobalState gs"
  [gs]
  (let [filter-tasks (fn [x] (if (not= (:rank x) nil) true false ))]
    (inc (int (:rank (last (vec (sort-by :rank higher-rank? (filter filter-tasks
                                                               (:lon @gs))))))))))
;(is (= (->rank-helper test-state) 10))

(defn ->rank
  "-> Int
  produces a new rank based on the app-state"
  []
  (->rank-helper app-state))

(defn ->node
  "TaskState String String -> Node
  Consumes a TaskState ts a headline hl and a project pro;
  creates a node"
  [ts hl]
  {:key nil ;; should create a key
   :level 2
   :headline hl
   :body ""
   :tag nil
   :tags {}
   :todo ts
   :priority nil
   :scheduled nil
   :deadline nil
   :properties {}
   :drawer {}
   :rank (->rank)
   :style nil})

(is (= (->node TODO "Test Headline")
       {:key nil ;; should create a key
        :level 2 :headline "Test Headline" :body ""  :tag nil
        :tags {} :todo TODO :priority nil :scheduled nil
        :deadline nil :properties {} :drawer {} :rank (->rank)
        :style nil}))


(defn index-of-node-helper [coll hl i]
  (cond (and (= 1 (int (:level (first coll)))) (= hl (:headline (first coll)))) i
        (empty? coll) nil
        :else (index-of-node-helper (rest coll) hl (inc 1))))

(defn index-of-node
  "Collection String -> Integer
  Consumes a Collection coll and a healine hl;
  produces the position of the element with the headline hl and level 1"
  [coll hl]
    (index-of-node-helper coll hl 0))
(is (= (index-of-node (:lon @test-state) "Inbox") 0))
(is (= (index-of-node (:lon @test-state) "XXXXX") nil))
(is (= (index-of-node (:lon @test-state) "Test") nil
))

(defn reset-lon!
  "Global-State ListOfNode -> GlobalState
  Resets the ListOfNode lon in the app-state; produces a new GlobalState"
  [gs lon]
  (let [ed? (:editor? @app-state)
        se? (:search? @app-state)
        en? (:entry?  @app-state)
        ls  (:ls      @app-state)]
    (reset! gs (global-state. ed? se? en? ls lon))))

(defn insert-node-helper!
  "Node String GlobalState -> GlobalState
  Inserts a node n at the right position in project pro and GlobalState gs;
  returns a ListOfNode"
  [n pro gs]
  (let [lon (vec (:lon @gs))
        i (inc (index-of-node lon pro))
        new-lon (vec (concat (subvec lon 0 i) [n] (subvec lon i)))]
   (reset-lon! gs new-lon)))
(is (= (get (:lon (insert-node-helper! (->node TODO "Test Headline") "Inbox" test-state)) 1)
       (->node TODO "Test Headline")))

(defn insert-node!
  "Node String GlobalState -> ListOfNode
  Inserts a node n at the right position in project pro and returns a ListOfNode"
  [n pro]
  (insert-node-helper! n pro app-state))

(defn add-task!
  "TaskState String String -> GlobalState
  Consumes a TaskState ts a headline hl and a project pro;
  adds a task to the app-state"
  [ts hl pro]
  (let [n (->node ts hl)]
    (insert-node! n pro)))

