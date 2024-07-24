;;;;given list of color blocks, sort them based on color
(define blocks (vector 'white_stained-glass 'black_stained_glass 'gray_stained_glass))
(define predict-vect-1 (vector 'white_stained_glass 'black_stained_glass 'gray_stained_glass))
(define predict-vect-2 (vector 'white_stained_glass 'black_stained_glass 'black_stained_glass 'gray_stained_glass))


(define (block-to-colorvalue block)
  (cond
    ;;;TODO: modify that it also works for other colors
    ((eq? block 'white_stained_glass) 0)
    ((eq? block 'gray_stained_glass) 1)
    ((eq? block 'black_stained_glass) 2)
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

(define (set-block! vector idx block line)
  ;TODO: let the robot move the block
  ;TODO: send to java client to highlight a line
  (define setBlock (method "setBlock" "siheynde.bachelorproefmod.util.FunctionCalledByScheme" "java.lang.String" "java.lang.Integer" "java.lang.Integer"))
  (setBlock (new "siheynde.bachelorproefmod.util.FunctionCalledByScheme") (.toString block) (new "java.lang.Integer" idx) (new "java.lang.Integer" line))
  (vector-set! vector idx block))

(define (let-loop name)
    ;TODO: define send to java client to highlight a line
    (define letLoop (method "letLoop" "siheynde.bachelorproefmod.util.FunctionCalledByScheme" "java.lang.String"))
    (letLoop (new "siheynde.bachelorproefmod.util.FunctionCalledByScheme") (.toString name))
    0
)

(define (end)
    (define done (method "done" "siheynde.bachelorproefmod.util.FunctionCalledByScheme"))
    (done (new "siheynde.bachelorproefmod.util.FunctionCalledByScheme"))
)

(define (get-block vector idx)
    ;TODO: move robot to the block
    ;TODO: send to java client to highlight a line
    (define getBlock (method "getBlock" "siheynde.bachelorproefmod.util.FunctionCalledByScheme" "java.lang.Integer"))
    (getBlock (new "siheynde.bachelorproefmod.util.FunctionCalledByScheme") (new "java.lang.Integer" idx))
    (vector-ref vector idx))

(define (amount-of-blocks vector)
    ;TODO: send to java client to highlight a line
    (vector-length vector))

 (define (insertion-sort vector <<?)
      (define (>=? x y) (not (<<? x y)))
      (let outer-loop
        ((outer-idx (- (amount-of-blocks vector) 2)))
        ;outer index start op lengte vector -2
        (let-loop 'outer-loop)
        (let ((current (get-block vector outer-idx)))
            ; current is element op outer idx
          (set-block! vector
           (let inner-loop
             ((inner-idx (+ 1 outer-idx))) ;inner loop begint op outer loop + 1
             (let-loop 'inner-loop)
             (cond
               ((or (>= inner-idx (amount-of-blocks vector))
                    (>=? (get-block vector inner-idx)
                         current))
                (- inner-idx 1)) ;inner idx -1 als inner idx op einde vector
               (else             ;of inner-idx > idx current
                                 ;             < => swap vorige inner met inner
                (set-block! vector (- inner-idx 1)
                    (get-block vector inner-idx) 90)
                (inner-loop (+ inner-idx 1))))) ;inner loop + 1
           current 79) ;swap current met inner-idx -1 (vorig)
          (if (> outer-idx 0)
              (outer-loop (- outer-idx 1)))))
          (end))


(define (predict)
   (define test (method "test" "siheynde.bachelorproefmod.util.FunctionCalledByScheme"))
   (test (new "siheynde.bachelorproefmod.util.FunctionCalledByScheme"))
   ;(display test)
   (+ 5 2)
) ;;TODO: use arguments for predict, make multiple predict functions,

(define (flatmap input)
    (define result '())
    (define (flatmap-1 input)
        (if (null? input) result
            (begin
                (set! result (cons (string->symbol (car input)) result))
                (flatmap-1 (cadr input))
            )
        )
    )
    (flatmap-1 input)
    (list->vector result)
)
                 ;; so you can also have predict that uses edge cases

(define (run input)
    (define input->inputvect (flatmap input))
    (insertion-sort input->inputvect lighter-than)
    (vector->list input->inputvect)
)


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
