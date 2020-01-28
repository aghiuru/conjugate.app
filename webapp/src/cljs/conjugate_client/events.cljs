(ns conjugate-client.events
  (:require [re-frame.core :as re-frame]
            [clojure.string :as string]
            [day8.re-frame.http-fx]
            [ajax.core :as ajax]
            [conjugate-client.db :as db]
            [conjugate-client.diacritics :as d9s]
            [conjugate-client.match :as match]
            [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced  [_ _]
   db/default-db))

(defn new-db-log [new-db]
  (prn :new-db new-db)
  new-db)

(re-frame/reg-event-db
 ::update-user-input
 (fn [db [_ input :as params]]
   (new-db-log (assoc-in db [:user :input] input))))

(re-frame/reg-event-db
 ::user-diacritics-changed
 (fn [db _]
   (new-db-log (update-in db [:user :diacritics?] not))))

(re-frame/reg-event-fx
 ::user-language-changed
 (fn [{:keys [db]} [_ language :as params]]
   {:db (new-db-log (assoc-in db [:user :lang] language))
    :dispatch-n [[::reset]
                 [::get-conjugation]]}))

(re-frame/reg-event-db
 ::reset
 (fn [db [_ _]]
   (new-db-log
    (-> db
        (assoc-in [:user :input] "")
        (assoc-in [:no-tries] 0)))))

(re-frame/reg-event-fx
 ::check
 (fn [{:keys [db]} params]

   ;; (prn params)
   (let [{{input :input d9s? :diacritics?} :user
          no-tries :no-tries
          {conjugation :conjugation} :conjugation} db]
     ;; (prn :check input conjugation)

     (if (match/check input conjugation d9s?)

       (do
         ;; (prn :check "match")
         {:db db
          :dispatch-n [[::reset]
                       [::get-conjugation]]})

       (do
         ;; (prn :check "inc tries")
         {:db (update-in db [:no-tries] inc)})))))

(re-frame/reg-event-fx
 ::get-conjugation
 (fn [{:keys [db]} _]                    ;; the first param will be "world"
   (let [lang (-> db :user :lang)]
     {:db   db
      :http-xhrio {:method          :get
                   ;; :uri             (str "http://localhost:8080/conjugation?lang=" lang)
                   :uri (str "https://owco7iwlk5.execute-api.us-east-1.amazonaws.com/dev/conjugation?lang=" lang)
                   :timeout         8000                                           ;; optional see API docs
                   :response-format (ajax/json-response-format {:keywords? true})  ;; IMPORTANT!: You must provide this.
                   :on-success      [::good-http-result]
                   :on-failure      [::bad-http-result]}})))

(re-frame/reg-event-db
 ::good-http-result
 (fn [db [_ result]]
   (assoc db :conjugation result)))

(re-frame/reg-event-db
 ::bad-http-result
 (fn [db [_ result]]
   (assoc db :api-result result)))
