(ns conjugate-client.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::name
 (fn [db] (:name db)))

(re-frame/reg-sub
 ::conjugation
 (fn [db] (:conjugation db)))

(re-frame/reg-sub
 ::state
 (fn [db] (:state db)))

(re-frame/reg-sub
 ::user
 (fn [db] (:user db)))

(re-frame/reg-sub
 ::no-tries
 (fn [db] (:no-tries db)))
