(ns chapter4.fp)

;; Guidelines for use:
;; 1. Avoid direct recursion.
;;    The JVM can't optimize recursive calls, and
;;    Clojure programs that recurse will blow their stack
;; 2. Use `recur` when you're producing scalar values or
;;    small, fixed sequences. Clojure _will_ optimize
;;    calls that use an explicit `recur`
;; 3. When producing large or variable-sized sequences,
;;    always be lazy. (Do _not_ recur.)
;;    Then, your callers can consume just the part of the
;;    sequence they actually need.
;; 4. Be careful not to realize more of a lazy sequence
;;    than you need.
;; 5. Know the sequence library.
;;    You can often write code without using `recur` or
;;    the lazy APIs at all.
;; 6. Subdivide.
;;    Divide even simple-seeming problems into smaller pieces
;;    and you'll often find solutions in the sequence library
;;    that lead to more general, reusable code.

;; Challenge: Convert a recursive definition into working code
;; You might do this in several ways:
;; - A simple recursion, using a function that calls itself
;;   in some way to implement the induction step.
;; - A tail recursion, using a function calling itself only
;;   at the tail end of its execution.
;;   Tail recursion enables an important optimization.
;; - A lazy sequence that eliminates actual recursion and
;;   calculates a value later, when it's needed.
;; Choosing the right approach is important.
;; In Clojure, being lazy is often the right approach.

; bad idea
(defn stack-consuming-fibo [n]
  (cond
    (= n 0) 0
    (= n 1) 1
    :else (+ (stack-consuming-fibo (- n 1))
             (stack-consuming-fibo (- n 2)))))
(comment
  ; Results in a StackOverflowError
  (stack-consuming-fibo 1000000))

