(ns conjugate-client.match
  (:require [clojure.string :as string]
            [conjugate-client.diacritics :as d9s]))

(defn check [input correct d9s?]
  (if d9s?
    (= input correct)
    (let [to-check (string/lower-case (d9s/strip-d9s input))
          to-check-against (string/lower-case (d9s/strip-d9s correct))]
      (prn :check input correct d9s? to-check to-check-against)
      (= to-check to-check-against))))
