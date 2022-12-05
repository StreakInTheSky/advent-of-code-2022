(require '[clojure.string :refer [split]])

(reduce max
		(map #(reduce + %)
			 (map #(map read-string %)
				  (map #(split % #"\n")
					   (split (slurp "../inputs1.txt") #"\n\n")))))




