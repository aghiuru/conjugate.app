(ns conjugate-client.db)

(def default-db
  {:state :input
   :user {:input ""
          :lang "cat"
          :diacritics? false}
   :no-tries 0
   :conjugation {:verb "renyir",
                 :person 2,
                 :tense :future,
                 :regular? true,
                 :conjugation "renyirà",
                 :tags ["third conjugation" "regular"],
                 :translations {"en-GB" "to argue; to quarrel; to twist; to wrangle", "es-ES" "reñir", "fr-FR" "gronder", nil nil},
                 :pronoun "ell, ella, vostè"}})
