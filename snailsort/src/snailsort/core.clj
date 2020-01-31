(ns snailsort.core)

(def directions
  {:right #(update-in % [1] inc)
   :down #(update-in % [0] inc)
   :left #(update-in % [1] dec)
   :up #(update-in % [0] dec)})

(def clockwise-direction
  {:right :down
   :down :left
   :left :up
   :up :right})

(defn next-coordinate
  "I return the next grid coordinate based on current position and direction"
  [direction current]
  ((directions direction) current))

(defn is-valid?
  "I return a boolean if a coordinate is valid or not"
  [grid coordinate]
  (some? (get-in grid coordinate)))

(defn next-direction
  "I return the next clockwise direction"
  [direction]
  (direction clockwise-direction))

(defn first-visit?
  "I check if a grid cell is being checked for the first time"
  [checked current]
  (nil? (checked current)))

(defn all-visits?
  "I check if all grid cells have been checked"
  [grid checked]
  (= (count (flatten grid))
     (count checked)))

(defn next-valid-coordinate
  "I return the next valid coordinate or nil if none exists"
  [grid current direction checked]
  (let [next-pos (next-coordinate direction current)
        new-direction (next-direction direction)
        rotated-pos (next-coordinate new-direction current)]
    (if (and (is-valid? grid next-pos)
             (first-visit? checked next-pos))
      next-pos
      (if (and (is-valid? grid rotated-pos)
               (first-visit? checked rotated-pos))
        rotated-pos
        nil))))