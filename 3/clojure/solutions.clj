(require '[clojure.string])
(require '[clojure.set])

(def rucksacks (clojure.string/split-lines (slurp "../inputs.txt")))

(defn half-sacks
  [sack]
  (let [m (int (/ (count sack) 2))]
    (split-at m sack)))

(defn sort-set
  [sack]
  (sort (set sack)))

(defn shared-type
  [[sack1, sack2]]
  (loop [sack1 (sort-set sack1) sack2 (sort-set sack2)]
    (let [[[h1 & remaining1] [h2 & remaining2]] [sack1 sack2]]
      (cond
        (< (int h1) (int h2)) (recur remaining1 sack2)
        (> (int h1) (int h2)) (recur sack1 remaining2)
        :else h1)
      )))

(defn shared
  [colls]
  (first (reduce clojure.set/intersection (map set colls))))

(def priorities [\a \b \c \d \e \f \g \h \i \j \k \l \m \n \o \p \q \r \s \t \u \v \w \x \y \z \A \B \C \D \E \F \G \H \I \J \K \L \M \N \O \P \Q \R \S \T \U \V \W \X \Y \Z])

(defn priority-value
  [item-type]
  (inc (.indexOf priorities item-type)))

(reduce + (map priority-value (map shared-type (map half-sacks rucksacks))))
(reduce + (map priority-value (map #(shared %) (map half-sacks rucksacks))))

(defn group-by-three
  [sacks]
  (loop [[one two three & remaining] sacks groups []]
    (let [new-groups (cons [one two three] groups)]
      (if (not-empty remaining)
        (recur remaining new-groups)
        new-groups))))

(defn shared-by-three
  [[sack1, sack2, sack3]]
  (let [[sorted1 sorted2 sorted3] [(sort-set sack1) (sort-set sack2) (sort-set sack3)]]
    (loop [set1 sorted1 set2 sorted2 set3 sorted3]
      (let [[[h1 & remaining1] [h2 & remaining2] [h3 & remaining3]] [set1 set2 set3]]
      (if (= h1 h2 h3)
        h1
        (let [[one two three] [(int h1) (int h2) (int h3)]]
            (condp = (min one two three)
              one (recur remaining1 set2 set3)
              two (recur set1 remaining2 set3)
              three (recur set1 set2 remaining3))))))))

(reduce + (map priority-value (map shared-by-three (group-by-three rucksacks))))
(reduce + (map priority-value (map #(shared %) (group-by-three rucksacks))))
