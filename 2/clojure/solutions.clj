(require '[clojure.string :refer [split]])

(def strategy (map #(split % #" ") (split (slurp "../inputs.txt"), #"\n")))

(def points {"X" 1, "Y" 2, "Z" 3})
(def losses {"A" "Z", "B" "X", "C" "Y"})
(def wins {"A" "Y", "B" "Z", "C" "X"})
(def draws {"A" "X", "B" "Y", "C" "Z"})

(defn result
  [a b]
  (if (= (wins a) b) 
    6
    (if (= (draws a) b)
      3
      0)))

(defn score1
  "get each first value and change those into a win/lose/draw score
  get each 2nd value and change those into a choice score
  add the two scores"
  [[opponent, me]]
  [(result opponent me) (points me)])

(reduce + (map #(reduce + %) (map score1 strategy)))

(def lose-draw-win 
  {
   "A" ["C", "A", "B"],
   "B" ["A", "B", "C"],
   "C" ["B", "C", "A"]
   })
(def score {"A" 1, "B" 2, "C" 3})

(defn score2
  "if plan is X add 0 with the score if the losing action for the opponent's move
   if plan is Y add 3 with the score of the draw action for the opponent's move
   If plan is Z add 6 with the score of the win action for the opoonent's move"
   [[move, plan]]
   (if (= plan "X")
     [0 (score ((lose-draw-win move) 0))]
     (if (= plan "Y")
       [3 (score ((lose-draw-win move) 1))]
       [6 (score ((lose-draw-win move) 2))])))

(reduce + (map #(reduce + %) (map score2 strategy)))

