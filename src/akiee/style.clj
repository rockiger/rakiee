(ns akiee.style
  (:require [garden.core :refer [css]]
            [garden.units :as u :refer [px pt]]))

;; Basic stylesheet for akiee

;; =================
;; Constants:

(def fc "#54595a") ; basic font color
(def bc "#fff") ; white background color

;; =================
;; Style:

(css {:output-to "css/style.css"}
     [:body {:margin 0
             :padding-top (px 52)
             :font-family "'Source Sans Pro', Cantarell, Ubuntu, 'Helvetica Neue', Helvetica, Calibri, Verdana, Arial, sans-serif"
             :font-size (px 16)
             :color fc
             :background-color bc
             :overflow-y "hidden"
             :height "100vh"}]

     
     [:.navbar-flex :#enter-task :#search-form :#editor {:display "flex"}]
     [:#editor [:textarea {:width "100%" :height "100%"}]]

     [:.spacer {:flex 1}]

     [:.slider {:overflow-y "hidden" :height (px 65)
                :transition "height .5s cubic-bezier(0, 1, 0.5, 1)"}]
     [:.slider.closed {:height (px 0) :transition "height .5s cubic-bezier(0, 1, 0.5, 1)"}])
