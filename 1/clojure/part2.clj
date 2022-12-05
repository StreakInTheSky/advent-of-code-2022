(require '[clojure.string :refer [split]])

(defn add-top-three
  [[top1 top2 top3 & _]]
  (+ top1 top2 top3))

(add-top-three
  (sort > (map #(reduce + %)
               (map #(map read-string %)
                    (map #(split % #"\n")
                         (split (slurp "../inputs1.txt") #"\n\n"))))))


