(ns accounting-period-close.core
  (:gen-class)
  (:require [org.httpkit.client :as http])
  (:require [clojure.data.json :as json])
  (require [clojure.string :as str]))
	
(defn build-accounting-period-close-command [fiscal-period-code fiscal-year]
	{ :description (str "Auto-Close Accounting Period " fiscal-period-code " " fiscal-year)
	  :universityFiscalYear fiscal-year
	  :universityFiscalPeriodCode fiscal-period-code })

(defn -main
  "Accounting Period Close clojure client."
  [fiscal-period-code fiscal-year & more-args]
  (let [received-response (atom false)]
	(println "Closing accounting period" fiscal-period-code fiscal-year)
	(http/post "http://localhost:8080/kfs-dev/coa/accounting_periods/close"
		{ :headers { "Authorization" "NSA_this_is_for_you" "Content-Type" "application/json" "Accept" "application/json" }
		  :body (json/write-str (build-accounting-period-close-command fiscal-period-code fiscal-year)) }
		(fn [{:keys [status headers body error]}]
			(let [response (json/read-str body)]
				(if (= 200 status)
					(if (boolean (get response "closed"))
						(println "Closed Accounting Period" fiscal-period-code fiscal-year ". Created eDoc #" (get response "documentNumber"))
						(println "Accounting period" fiscal-period-code fiscal-year "was already closed"))
					(let [error-messages (str/join "\n" response)]
						(println "Error" status "occurred.\n" error-messages)))
				(swap! received-response (fn [_] true)))))
	(while (not @received-response) (Thread/sleep 200))
))
