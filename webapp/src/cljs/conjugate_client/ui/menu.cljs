(ns conjugate-client.ui.menu
  (:require [re-frame.core :as re-frame]
            [conjugate-client.events :as events]))

(defn menu [user-lang]
  [:nav {:class "navbar navbar-expand navbar-light"}
   [:a {:class "navbar-brand" :href "#"}
    [:div {:style {:font-family :cursive}} "conjugate"]]
   [:div {:id "navbarSupportedContent"
          :class "collapse navbar-collapse"}
    (let [lang user-lang]
      [:ul {:class "navbar-nav mr-auto"}
       [:li {:class (if (= lang "cat") "nav-item active" "nav-item")}
        [:a {:class "nav-link"
             :href "#"
             :on-click #(re-frame/dispatch [::events/user-language-changed "cat"])}
         "cat"
         [:span {:class "sr-only"} "current"]]]
       [:li {:class (if (= lang "es") "nav-item active" "nav-item")}
        [:a {:class "nav-link"
             :href "#"
             :on-click #(re-frame/dispatch [::events/user-language-changed "es"])}
         "es"
         [:span {:class "sr-only"} "current"]]]
       [:li {:class (if (= lang "fr") "nav-item active" "nav-item")}
        [:a {:class "nav-link"
             :href "#"
             :on-click #(re-frame/dispatch [::events/user-language-changed "fr"])}
         "fr"
         [:span {:class "sr-only"} "current"]]]
       [:li {:class (if (= lang "it") "nav-item active" "nav-item")}
        [:a {:class "nav-link"
             :href "#"
             :on-click #(re-frame/dispatch [::events/user-language-changed "it"])}
         "it"
         [:span {:class "sr-only"} "current"]]]])]])
