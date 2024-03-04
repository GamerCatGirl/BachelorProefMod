(define (insertion-sort vector <<?)
   (define (>=? x y) (not (<<? x y))) ; We hebben >=? eigenlijk niet meer nodig hier.
   (let outer-loop ; buitenste loop
     ((outer-idx 1)) ; iteratievariabele
     (let ; begin body
         ((current (vector-ref vector outer-idx)))
       (vector-set!
        vector
        (let inner-loop ; binnenste loop
          ((inner-idx (- outer-idx 1))) ; iteratievariabele
           (cond ; begin body
            ((or (<= inner-idx -1)
                 (<<? (vector-ref vector inner-idx)
                      current))
             (+ inner-idx 1))
            (else
             (vector-set! vector (+ inner-idx 1) (vector-ref vector inner-idx))
             (inner-loop (- inner-idx 1)))))
        current)
       (if (< outer-idx (- (vector-length vector) 1))
         (outer-loop (+ outer-idx 1))))))