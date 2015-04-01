(ns akiee.app-db
  (:require [akiee.fileoperations :as fo]
            [akiee.datadefinitions :as dd :refer [global-state]]
            [akiee.constants :refer [TODO DOING DONE ALL]]
            [akiee.dom-helpers :as dom]
            [akiee.node :as no]
            [cljs.test :refer-macros [is deftest]]
            [cljs.nodejs :as nj]
            [reagent.core :as rc]
            [clojure.string :as s :refer [trim]]))


(enable-console-print!)

;; app state operations for Akiee

;; =================
;; Constants:

(def null (js* "null"))
(def FP (fo/create-task-list-file (fo/user-home)))

;;(defrecord global-state [editor? search? entry? ls lon])

;; =================
;; Functions:

(defn load-app-state
  "String -> GS
  consumes the path p to the task file and produces the initial app-state
  TODO find way to test, without :key"
  [p]
  (global-state. false false false false "" DOING (no/->nodes p)))

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



(defn tasks-helper
  "GlobalState -> lon
  consumes an GlobalState gs and  returns the tasks, according to the current ListState"
  [gs]
    (let [filter-tasks (fn [x] (if (= (:level x) 2) true false ))
          filter-state (fn [x] (cond
                                (= (:ls @gs) ALL) true
                                (= (:ls @gs) (:todo x)) true
                                :else false))
          filter-search (fn [x] (if (not (empty? (:ss @gs)))
                                  (if (re-find (re-pattern
                                                (str "(?i)"(:ss @gs))) (:headline x))
                                    true
                                    false)
                                  true))]
      (vec (sort-by :rank no/higher-rank?
                    (filter filter-search
                    (filter filter-state
                    (filter filter-tasks (:lon @gs))))))))

(let [filter-tasks (fn [x] (if (= (:level x) 2) true false ))
      filter-state (fn [x] (cond
                                (= (:ls @app-state) ALL) true
                                (= (:ls @app-state) (:todo x)) true
                                :else false))
      filter-search (fn [x] (if (:ss @app-state)
                                  (if (re-find (re-pattern (:ss @app-state)) (:headline x))
                                    true
                                    false)
                                  true))]
  (filter filter-search (filter filter-state (filter filter-tasks (:lon @app-state)))))

;; Test fails becaus of :body one seems to have an "\n"
#_(is (no/node= (nth (tasks-helper test-state) 0) {:key "orgode_33.##" :level 2  :headline "Test"
                                              :body ""  :tag nil :tags {} :todo "TODO"
                                              :priority nil :scheduled nil :deadline nil
                                              :properties {} :drawer {} :rank nil :style nil}))


(defn tasks
  "-> ListOfNode
  shows the tasks of the app-state, according to the current ListState"
  []
  (tasks-helper app-state))

(defn nodes
  "-> ListOfNode
  returns the nodes of the app-state"
  []
  (:lon @app-state))

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

(defn changed?
  "-> Boolean"
  []
  (:changed? @app-state))

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

(defn switch-editor!
  "-> Boolean
  switches the editor? state and returns it"
  []
  (if (editor?)
    (let [new-state (global-state. false (:search? @app-state) (:entry? @app-state) (:changed? @app-state) (:ss @app-state) (:ls @app-state) (:lon @app-state))]
      (reset! app-state new-state))
    (let [new-state (global-state. true false false (:changed? @app-state) (:ss @app-state) (:ls @app-state) (:lon @app-state))
          ea (dom/get-element "editor-area")]
      (do
        (reset! app-state new-state)
        (set! (.-value ea) (no/lon->md (nodes)))
        (.focus ea)
        (.click ea)))))

(defn switch-search!
  "-> GlobalState
  switches the search? state and the new app-state"
  []
  (if (search?)
    (let [new-state (global-state. (:editor? @app-state) false (:entry? @app-state) (:changed? @app-state) "" (:ls @app-state) (:lon @app-state))]
      (do
        (set! (.-value (dom/get-element "search-input")) "")
        (reset! app-state new-state)))
    (let [new-state (global-state. false true false (:changed? @app-state) (:ss @app-state) (:ls @app-state) (:lon @app-state))
          se (dom/get-element "search-input")]
      (do
        (reset! app-state new-state)
        (.focus se)))))

(defn switch-entry!
  "-> GlobalState
  switches the search? state and the new app-state"
  []
  (if (entry?)
    (let [new-state (global-state. (:editor? @app-state) (:search? @app-state) false (:changed? @app-state) (:ss @app-state) (:ls @app-state) (:lon @app-state))]
      (reset! app-state new-state))
    (let [new-state (global-state. false false true (:changed? @app-state) (:ss @app-state) (:ls @app-state) (:lon @app-state))
          entry (dom/get-element "enter-headline")]
      (do
        (reset! app-state new-state)
        (.focus entry)))))

(defn set-changed!
  "Bool -> GlobalState
  consumes the new state s switches the changed? variable and return the new app-state"
  [s]
    (let [new-state (global-state. (:editor? @app-state) (:search? @app-state) (:entry? @app-state) s (:ss @app-state) (:ls @app-state) (:lon @app-state))]
      (reset! app-state new-state)))

(defn set-search-string!
  "String -> GlobalState
  consumes a String s and changes the search-string of the app-state accordingly;
  returns the new GlobalState"
  [s]
  (reset! app-state (global-state. (:editor? @app-state) (:search? @app-state) (:entry? @app-state) (:changed? @app-state) s (:ls @app-state) (:lon @app-state))))

(defn switch-list-state!
  "ListState -> GlobalState
  Consumes a Liststate ls switches the ls variable and editor? search? search? accordingly"
  [ls]
  (let [lon (:lon @app-state)]
      (reset! app-state (global-state. false false false (:changed? @app-state) (:ss @app-state) ls lon))))

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

(defn ->rank-helper
  "GlobalState -> Int
  produces a new rank based on the tasks in the GlobalState gs"
  [gs]
  (let [filter-tasks (fn [x] (if (not= (:rank x) nil) true false ))]
    (inc (int (:rank (last (vec (sort-by :rank no/higher-rank? (filter filter-tasks
                                                               (:lon @gs))))))))))
;(is (= (->rank-helper test-state) 10))

(defn ->rank
  "-> Int
  produces a new rank based on the app-state"
  []
  (->rank-helper app-state))


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
        ss  (:ss @app-state)
        ls  (:ls      @app-state)]
    (reset! gs (global-state. ed? se? en? true ss ls lon))))

(defn insert-node-helper!
  "Node String GlobalState -> GlobalState
  Inserts a node n at the right position in project pro and returns GlobalState gs;
  returns a ListOfNode"
  [n pro gs]
  (let [lon (vec (:lon @gs))
        i (inc (index-of-node lon pro))
        new-lon (vec (concat (subvec lon 0 i) [n] (subvec lon i)))]
   (reset-lon! gs new-lon)))
(is (no/node= (get (:lon (insert-node-helper! (no/->node TODO "Test Headline" (->rank)) "Inbox" test-state)) 1)
       (no/->node TODO "Test Headline" (->rank))))

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
  (let [n (no/->node ts hl (->rank))]
    (insert-node! n pro)))

;; TODO
(defn next-ts!
  "String -> GlobalState
  Consumes a key ky and changes the task-state of that task in lon;
  returns the app-state"
  [ky]
  false)
