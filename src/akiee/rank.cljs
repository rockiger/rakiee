(ns akiee.rank
  (:require
   [akiee.app-db :as db]
   [akiee.constants :refer [TODO DOING DONE ALL]]
   [akiee.node :as no]
   [cljs.test :refer-macros [is deftest]]
   ))

;; Functions that that change rank of nodes

;; =================
;; Functions:

(defn task-by-pos
  "Integer -> Node
  Returns node at pos"
  [pos]
  (nth (db/tasks) pos))

(defn move-rank!
  "String Integer Integer -> ?"
  [ky sp tp direction]
  (let [source-task (task-by-pos sp)
        target-task (task-by-pos tp)
        source-rank (:rank source-task)
        target-rank (:rank (try
                               (task-by-pos tp)
                               (catch js/Error e (println e))))
        pred? (if (= direction "up")
                (fn [x] (if (and (>= (:rank x) target-rank)(< (:rank x) source-rank))
                          (assoc x :rank (inc (:rank x)))
                          x))
                (fn [x] (if (and (<= (:rank x) target-rank) (> (:rank x) source-rank))
                          (assoc x :rank (dec (:rank x)))
                          x)))
        new-lon (vec (map pred? (db/nodes)))
        new-task (assoc source-task :rank target-rank)
        np (db/node-pos-by-key ky (db/nodes))
        newer-lon (assoc new-lon np new-task)
        ]
    (when target-rank
      (println newer-lon)

      (println source-rank target-rank)
      (println new-task)
      (db/reset-lon! db/app-state newer-lon)
      )))
;; set on-click-node with target-rank
;; edge cases abfangen

(defn up-rank
  "String ->
  Consumes a key-String ky;
  changes the rank of the corresponding node to rank higher"
  [ky]
  (move-rank! ky 10 9 "up"))

(defn down-rank
  "String ->
  Consumes a key-String ky;
  changes the rank of the corresponding node to rank lower"
  [ky]
  (let [sp (db/node-pos-by-key ky (db/tasks))
        tp (inc (db/node-pos-by-key ky (db/tasks)))]
    (move-rank! ky sp tp "down")))

