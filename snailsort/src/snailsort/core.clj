(ns snailsort.core)

(def directions
  {:right #(update-in % [1] inc)
   :down #(update-in % [0] inc)
   :left #(update-in % [1] dec)
   :up #(update-in % [0] dec)})

(defn next-coordinate
  "I return the next grid coordinate based on current position and direction"
  [direction current]
  ((directions direction) current))

(defn is-valid?
  "I return a boolean if a coordinate is valid or not"
  [grid coordinate]
  (some? (get-in grid coordinate)))