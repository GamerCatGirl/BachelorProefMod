;;;;given list of color blocks, sort them based on color
(define blocks (vector 'white-stained-glass 'tinted-glass 'gray-stained-glass))
(define predict-vect-1 (vector 'white-stained-glass 'tinted-glass 'gray-stained-glass))
(define predict-vect-2 (vector 'white-stained-glass 'tinted-glass 'tinted-glass 'gray-stained-glass))


(define (block-to-colorvalue block)
  (cond
    ;;;TODO: modify that it also works for other colors
    ((eq? block 'white-stained-glass) 0)
    ((eq? block 'tinted-glass) 1)
    ((eq? block 'gray-stained-glass) 2)
    (else (error "unknown block"))))

(define lighter-than (lambda (block-1 block-2)
    (define value-bloc1 (block-to-colorvalue block-1))
    (define value-bloc2 (block-to-colorvalue block-2))
    (< value-bloc1 value-bloc2)))

(define darker-than (lambda (block-1 block-2)
    ;;;TODO: implment this function
    true))



;; MODIFY 1
;; In our version of from-light-to-dark, it only works for white, grey and black.
;; Make sure it aslo works for other colors.

;; MODIFY 2
;; Make a version darker-then that sorts from dark to light.
;; Hint: you can use the lighter-then procedure

;; MODIFY 3
;; In our version of the insertion sort procedure,
;; the outer loop runs from the end of the vector towards the start of the vector.
;; The order of the inner loop is the opposite.
;; Rewrite the procedure so that the order of the loops is reversed.
;; Which order will be the easiest to apply to single linked lists?

 (define (insertion-sort vector <<?)
      (define (>=? x y) (not (<<? x y)))
      (let outer-loop
        ((outer-idx (- (vector-length vector) 2)))
        ;outer index start op lengte vector -2
        (let ((current (vector-ref vector outer-idx)))
            ; current is element op outer idx
          (vector-set!
           vector
           (let inner-loop
             ((inner-idx (+ 1 outer-idx))) ;inner loop begint op outer loop + 1
             (cond
               ((or (>= inner-idx (vector-length vector))
                    (>=? (vector-ref vector inner-idx)
                         current))
                (- inner-idx 1)) ;inner idx -1 als inner idx op einde vector
               (else             ;of inner-idx > idx current
                                 ;             < => swap vorige inner met inner
                (vector-set! vector (- inner-idx 1) (vector-ref vector inner-idx))
                (inner-loop (+ inner-idx 1))))) ;inner loop + 1
           current) ;swap current met inner-idx -1 (vorig)
          (if (> outer-idx 0)
              (outer-loop (- outer-idx 1))))))


(define (predict)
   (define test (method "test" "siheynde.bachelorproefmod.util.FunctionCalledByScheme"))
   (test (new "siheynde.bachelorproefmod.util.FunctionCalledByScheme"))
   ;(display test)
   (+ 5 2)

) ;;TODO: use arguments for predict, make multiple predict functions,
                           ;; so you can also have predict that uses edge cases

(define predict-1 (insertion-sort predict-vect-1 lighter-than))
(define predict-2 (insertion-sort predict-vect-2 lighter-than))
                           ;;don't use function names "predict" but real function names

(define (modify) ;;;or complete a certain function
   (vector 7 9 4 8 3 0 1 2)
   (vector 7 9 4 4 3 0 1 2)
   (robot-move 2 3 4))

(define (robot-move delta-x delta-y delta-z)
  (list "move" delta-x delta-y delta-z))

(define (robot-take block)
  (list "take" block))

(define (robot-place block)
  (list "place" block))


;;TODO: call java from scheme
;;TODO: use VScode call to highlight lines when executing -- in run fase

;;TODO: use unit test to test end of primm fase in real world


;;Sort based on color, 7 blocks of color of rainbow
;;first stage sort 3 blocks and highlight what happen
;;modify the light to dark from dark to light
;;make now a function that sorts all colors

;(or use the minecraft categories of items)

;;TODO: in report connection between PRIMM, GAME-BASED learning, ....  and what we implemented
;;TODO: in report technical information about the implementation