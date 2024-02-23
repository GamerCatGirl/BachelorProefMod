(define (predict) (+ 5 2))

(define (modify)
   (move 2 3 4))

(define (move delta-x delta-y delta-z)
  (list "move" delta-x delta-y delta-z))
