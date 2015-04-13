(ns akiee.node
  (:require
   [akiee.fileoperations :as fo]
   [akiee.datadefinitions :as dd]
   [akiee.constants :refer [TODO DOING DONE ALL]]
   [cljs.test :refer-macros [is deftest]]
   [cljs.nodejs :as nj]
   [clojure.string :as s :refer [trim]]
   ))

;; Nodejs modules
(def org (nj/require "./lib/markdown-org-mode-parser"))

(def parse-file (.-parseBigString org))

;; Functions that create, convert and compare nodes

;; =================
;; Functions:

(defn ->key
  "Nil -> String
  Returns a unique key for new nodes"
  []
  (str (gensym "node_")))
(is (->key))

(defn node=
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

(is (= (node= dd/N1 dd/N1a) true))
(is (= (node= dd/N1 dd/N2)  false))

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

(defn jsnode->node
  "JsNode -> Node
  consumes a javascript org-node (object) jn and produces a node
  TODO make this more robust and general"
  [jn]
  (let [rank (aget jn "rank")]
    {:key (aget jn "key")
     :level (aget jn "level")
     :headline (str (aget jn "headline"))
     :body (if (and (> (count (trim (aget jn "body"))) 0) (not= (aget jn "body") "\n"))
                 (trim (aget jn "body")) nil)
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

;;(is (no/node= (jsnode->node dd/jsN1) dd/N1))
(is (= (:key (jsnode->node dd/jsN1)) (:key dd/N1)))


(defn ->node
  "TaskState String String -> Node
  Consumes a TaskState ts a headline hl, a project pro, a Rank r;
  creates a node"
  [ts hl r]
  {:key (->key)
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
   :rank r
   :style nil})

(is (node= (->node TODO "Test Headline" 10)
       {:key nil
        :level 2 :headline "Test Headline" :body ""  :tag nil
        :tags {} :todo TODO :priority nil :scheduled nil
        :deadline nil :properties {} :drawer {} :rank 10
        :style nil}))

(defn ->nodes
  "String -> ListOfNodes
  consumes the path p to the task file and produces a list of nodes
  TODO find way to test, without :key"
  [p]
  (let [nodes-array (parse-file (fo/load-task-file p))]
    (map jsnode->node (array->vec [] nodes-array))))


(defn lon->md [lon]
  (if (empty? lon)
    ""
    (let [n (first lon)]
      (str
       (if (= (:level n) 1) "# " "## ")
       (cond (:todo n) (str (:todo n) " "))
       (trim (:headline n)) "\n"
       (cond (not-empty (:body n)) (str (:body n) "\n"))
       (cond (:rank n) (str "RANK: "(:rank n) "\n"))
       (lon->md (rest lon))))))
(is (= (lon->md [(->node TODO "Ueberschrift" 1)]) "## TODO Ueberschrift\nRANK: 1\n"))

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

