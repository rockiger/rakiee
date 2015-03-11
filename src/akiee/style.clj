(ns akiee.style
  (:require [garden.core :refer [css]]
            [garden.units :as u :refer [px pt percent]]))

;; Basic stylesheet for akiee

;; =================
;; Constants:

(def fc "#54595a") ; basic font color
(def bc "#fff") ; white background color
(def dark-grey "#54595a")
(def medium-grey "#a1a1a1")
(def light-blue "#4a90d9")

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

     [:navbar-header {:width (percent 100) :height (px 52)}]
     [:.navbar-flex :#enter-task :#search-form :#editor {:display "flex"}]
     [:.navbar-default {:background-color "#ededed" :background-image "none"
                        :border-color medium-grey}]

     [:.spacer {:flex 1}]

     [:.slider {:overflow-y "hidden" :height (px 65)
                :transition "height .5s cubic-bezier(0, 1, 0.5, 1)"}]
     [:.slider.closed {:height (px 0) :transition "height .5s cubic-bezier(0, 1, 0.5, 1)"}]


     [:#editor [:textarea {:width "100%" :height "100%"}]]
     [:#editor :#list {:margin 0 :position "absolute" :top (px 52) :bottom 0 :left 0 :right 0}]
     [:#list :table :tbody :tr {:width (percent 100)}]
     [:#list {:table-layout "fixed" :overflow-y "auto"}]
     [:#list:hover {:outline "none"}]

     [:.nav :.pagination :.carousel :.hover-button {:cursor "pointer"}]

     [:.table>thead>tr>th :.table>tbody>tr>th :.table>tfoot>tr>th
      :.table>thead>tr>td :.table>tbody>tr>td :.table>tfoot>tr>td {:vertical "middle"}]

     [:.kanban-column {:background-color "#ddd"}]
     [:.kanban-column [:tr {:background-color "#fff"}]]
     [:.kanban-column [:td {:padding (px 8) :line-height 1.428571143
                            :border-top "1px solid #ddd"}]]
     [:#kanban-row:hover {:background-color "transparent" :cursor "default"}]

     [:.todo [:td {:background-color "#d9edf7 !important" :color "#31708f"}]]
     [:.doing [:td {:background-color "#dff0d8 !important" :color "#3c763d"}]]
     [:.done [:td {:color "#777 !important" :text-decoration "line-through"}]]
     [:.selected {:background-color "#729FCF" :color "white"}]

     [(keyword "tr:nth-child(2)") {:width (percent 100)}]
     [:tr:hover {:background-color "#f2f2f2"}]
     [:tr.selected:hover {:background-color "#4281c3"}]

     [:.table>thead>tr>th.kanban-column :.table>tbody>tr>th.kanban-column
      :.table>tfoot>tr>th.kanban-column :.table>thead>tr>td.kanban-column
      :.table>tbody>tr>td.kanban-column :.table>tfoot>tr>td.kanban-column
      {:width (percent 30) :vertical-align "top"}]

     [:#enter-task-div :#search-form {:background-color "#d6d6d6"
                                      :border-bottom "1px solid #a1a1a1" }]
     [:#search-form {:align-items "center" :justify-content "center"}]
     [:#enterTask :#search-form.row {:margin-top (px 20)}]
     [:#enterTask [:div {:padding-right 0}]]

     [:#search-input {:padding-left (px 30) :width (px 300)}]
     [:#search-input-icon {:position "absolute" :left (px 0) :padding [(px 8) (px 27)]}]

     [:.empty-list-image {:overflow "hidden" :height (percent 100)}]
     [:.empty-list {:vertical-align "middle" :text-align "center" :position "fixed"
                    :top (percent 30) :width (percent 100)}]

     ;; Bootstrap-Theme Override

     [:.btn :.btn:active :.btn:focus
      {:background-image "linear-gradient(to bottom, #fafafa 0%, #ededed 40%, #e0e0e0 100%)"
       :border-radius (px 3) :color "#2e3436" :box-shadow "inset 0 1px white, 0 1px white"
       :margin-left (px 3) :font-size (px 16)  :line-height (px 14)}]
     [:.btn:focus :.btn:focus:active :.btn.active:focus :.btn-group>.btn:focus
      :.btn-group-vertical>.btn:focus {:outline-color "rgba(46,52,52,0.3)"
                                       :outline-offset (px -4) :outline-style "dashed"
                                       :outline-width (px 1)}]
     [:.btn.active :.btn.active:hover :.btn:hover:active
      {:background-image "linear-gradient(#d6d6d6, #e0e0e0)" :border-radius "3px"
       :color "#2e3436" :box-shadow "none" :font-size "16px"}]

    [:.btn-default :.btn-default:focus :.btn-default:active :.btn-default:focus:active
     {:border-color medium-grey :icon-shadow "0 1px rgba(256, 256, 256, 0.76923)"
      :text-shadow "0 1px rgba(256, 256, 256, 0.76923)" :outline-offset (px -4)
      :padding "8px 16px" :transition "all 200ms ease-out"
      :background-color "transparent" :background-position 0}]
    [:.btn-default:hover
     {:background-image "linear-gradient(to bottom, white 0%, #f7f7f7 40%, #ededed 100%)"
      :background-position 0}]
    [:.btn-default.active :.btn-default.active:hover :.btn-default:hover:active
     {:border-color medium-grey :icon-shadow "0 1px rgba(256, 256, 256, 0.76923)"
      :text-shadow "0 1px rgba(256, 256, 256, 0.76923)"
      :outline-offset (px -4)
      :transition "all 200ms ease-out"}]
    [:.btn-square :.btn-square:hover :.btn-square.active :.btn-square.active:hover
     :.btn-square:active :.btn-square:hover:active :btn-square:focus
     {:padding "8px 9px !important" :font-size "13px !important"
      :margin-left "6px !important"}]

    [:.btn-link :.btn-link:focus :btn-link:active
     {:text-decoration "underline" :background "none" :color "#2a76c6"
      :box-shadow "none"}]
    [:.btn-link:hover {:color light-blue}]

    [:#show-enterTask {:padding "8px 8px 8px 10px !important"}]
    [:*:before {:font-size (px 13)}]

    [:table [:td [:.glyphicon-chevron-up {:border "1px solid transparent"
                                         :padding (px 8) :padding-left (px 9)
                                         :color dark-grey}]]]
    [:.glyphicon-chevron-down :.hover-button {:border "1px solid transparent"
                                              :padding (px 8) :padding-left (px 9)
                                              :color dark-grey}]
    [:table [:td [:.glyphicon-chevron-down {:padding-left (px 8) :color dark-grey}]]]
     (let [st {:border (str "1px solid " medium-grey) :border-radius (px 3)
                   :background-image
                   "linear-gradient(to bottom, white 0%, #f7f7f7 40%, #ededed 100%)"}]
      [[:table [:td [:.glyphicon-chevron-up:hover :.glyphicon-chevron-down:hover st]]]
      [:.hover-button:hover st]])

    [:btn-group>.btn:first-child :.btn-group>.btn:last-child {:width (px 100)}]
    [:.btn-group>.btn {:width (px 98) :height (px 32) :padding-top "7px !important"}]

     [:.todo :.doing :.done :.selected :tr:hover [:td [:glyphicon-chevron-up :glyphicon-chevron-down {:border-color "transparent"}]]]

     [:table [:td [:.glyphicon-chevron-up:focus :.glyphicon-chevron-down:focus
                   {:outline "rgba(46, 52, 54, 0.3) 1px dashed !important"
                    :outline-offset (px -4)}]]]

     [:.form-control {:border "1px solid medium-grey" :border-radius (px 3)
                      :box-shadow "inset 0 3px transparent" :background-color "transparent"
                      :background-image "linear-gradient(#f7f7f7, #ffffff 90%)"
                      :transition "all 200ms ease-out"}]
     [:.form-control:focus {:border-color light-blue
                            :box-shadow "inset 0 3px rgba(0, 0, 0, 0.02)"}]

     )