(ns akiee.sidebar
  (:require [reagent.core :as r]
            [akiee.constants :as c :refer [TODO DOING DONE ALL]]
            [akiee.app-db :as db]
            [akiee.handlers :as h]))

(enable-console-print!)

;; TODO unit test for reagent
;; TODO figwhell for node-webkit

;; =================
;; Functions:

(defn headline [node]
     [:h4 {:on-click h/onclick-hdln}
      (if (and (db/selected) (= (db/editable) "hdln"))
            [:input#sidebar-headline.sidebar-input.form-control {:type "text" :default-value (:headline node) :on-blur h/onblur-sidebar-input :on-submit h/onblur-sidebar-input}]
            (:headline node))
      [:span.fa.fa-pencil-square-o]])

(defn body [node]
     [:div#sidebar-body {:on-click h/onclick-body}
      [:span.details-left "Details:"] [:span.fa.fa-file-text-o]
      (if (and (db/selected) (= (db/editable) "body"))
              [:div
               [:textarea#sidebar-body-ta.sidebar-input.form-control {:default-value (:body node) :on-blur h/onblur-sidebar-body :on-submit h/onblur-sidebar-body}]
               [:button#sidebar-body-submit.btn.btn-default {:type "button" :title "Tab" :style {:float "right" :margin-top "5px"}} "Save"]]
            [:div [:pre (:body node)]])])

(defn sidebar []
  (let [node (db/sidebar-content)]
    [:div#details
     (headline node)
     [:div
      [:span.details-left "Planned:"] [:span (if (:scheduled node) (:scheduled node) "Never")] [:span.fa.fa-calendar]]
     [:div
      [:span.details-left "Repeat:"] [:span "Never"] [:span.fa.fa-repeat]]
     [:div
      [:span.details-left "Due:"] [:span (if (:deadline node) (:deadline node) "Never")] [:span.fa.fa-calendar]]
     [:div
      [:span.details-left "Tags:"] [:span (if (:tags node) (:tags node) "None")] [:span.fa.fa-tags]]
     [:div
      [:span.details-left "State:"] [:span (:todo node)] [:span.fa.fa-check-square-o]]
     [:div
      [:span.details-left "Project:"] [:span (:project node)] [:span.fa.fa-list-alt]]
     (body node)]))
