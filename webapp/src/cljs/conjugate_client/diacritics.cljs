(ns conjugate-client.diacritics
  (:require [clojure.string :as str]))

(def diacritics-cat
  {\à \a
   \é \e
   \í \i
   \· \.
   \ç \c
   \ï \i})

(def diacritics-es
  {\á \a
   \é \e
   \í \i
   \ó \o
   \ú \u
   \ü \u
   \ñ \n})

(def diacritics-map
  (merge
   diacritics-cat
   diacritics-es))

(defn strip-d9s [s]
  (reduce (fn [s [re replacement]]
            (str/replace s re replacement))
          s diacritics-map))
