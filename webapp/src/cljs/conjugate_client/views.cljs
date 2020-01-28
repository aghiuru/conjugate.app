(ns conjugate-client.views
  (:require [re-frame.core :as re-frame]
            [conjugate-client.subs :as subs]
            [conjugate-client.events :as events]
            [clojure.string :as str]
            [conjugate-client.ui.menu :refer [menu]]))

(defn main-panel []
  (let [conjugation-map (re-frame/subscribe [::subs/conjugation])
        state (re-frame/subscribe [::subs/state])
        user (re-frame/subscribe [::subs/user])
        no-tries (re-frame/subscribe [::subs/no-tries])]
    (fn []

      [:div {:class "container h-100"}

       (menu (:lang @user))

       [:div {:class "col-12 col-sm-auto"}
        [:div {:class "row justify-content-center align-self-center"}
         [:div {:class "card-body"}
          [:input {:id "set-diacritics"
                   :type :checkbox
                   :checked (@user :diacritics?)
                   :on-click #(re-frame/dispatch [::events/user-diacritics-changed])}]
          " "
          [:label {:for "set-diacritics"} [:span {:style {:font-size :.75em}} "Check accents"]]]]]

       [:div {:class "container d-flex h-50"}
        [:div {:class "col-12 mx-auto"}
         (let [{:keys [verb
                       person
                       pronoun
                       conjugation
                       tense
                       translations
                       tags
                       regular?]} @conjugation-map
               tries @no-tries
               show-hint? (> tries 2)]

           [:div
            [:div {:style {:text-align :center}}
             [:span
              {:style {:font-family :cursive
                       :font-size :3em
                       :align :center}}
              verb]
             [:p (map (fn [t] ^{:key t} [:span [:span {:class "badge badge-primary"} t] " "]) tags)]
             [:p {:style {:font-size :.75em}} (str/join " / " (filter some? (map second translations)))]
             [:strong (str (name tense) " (" (if regular? "regular" "irregular") ")")]
             ]

            [:div {:style {:text-align :center}}
             [:strong pronoun]]

            [:div {:style {:text-align :center}}
             [:input {:type :text
                      :value (@user :input)
                      :on-change #(re-frame/dispatch [::events/update-user-input (-> % .-target .-value)])
                      :on-key-press (fn [e]
                                      (println "key press" (.-charCode e))
                                      (if (= 13 (.-charCode e))
                                        (re-frame/dispatch [::events/check])
                                        ; (re-frame/dispatch [::events/get-conjugation])
                                        ))}]
             " "
             [:strong {:style {:color :red}}
              (when-not show-hint?
                (repeat @no-tries "❗️"))]]

            [:div {:class "pt-4"
                   :style {:text-align :center}}
             [:strong {:style {:color :red}}
              (when show-hint? conjugation)]]])
         ;; [:div (str @conjugation-map)]
         ]]])))
