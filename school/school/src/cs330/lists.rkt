;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-beginner-reader.ss" "lang")((modname lists) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #f #t none #f ())))
;check-temps1 : (listof number) -> boolean
;Consumes a list of temperature measures and checks
; whether all measurements are between 5 and 95 degrees
; celsius (inclusively.)
(define (check-temps1 temps)
  (cond
    [(empty? temps)
     true]
    [(cons? temps)
     (cond
       [(and
         (>= (first temps) 5)
         (<= (first temps) 95)
         (check-temps1 (rest temps)))
        true]
       [else false])]))
(check-expect (check-temps1 empty)
              true)
(check-expect (check-temps1 (cons 5 empty))
              true)
(check-expect (check-temps1 (cons 95 empty))
              true)
(check-expect (check-temps1 (cons 4 empty))
              false)
(check-expect (check-temps1 (cons 96 empty))
              false)
(check-expect (check-temps1 (cons 5(cons 25(cons 95 empty))))
              true)
(check-expect (check-temps1 (cons 4(cons 25(cons 95 empty))))
              false)
(check-expect (check-temps1 (cons 5(cons 25(cons 96 empty))))
              false)

;check-temps : (listof number) number number -> boolean
;Consumes a list of temperature measures and checks
; whether all measurements are between low and high
; degrees celsius (inclusively.)
(define (check-temps temps low high)
  (cond
    [(empty? temps)
     true]
    [(cons? temps)
     (cond
       [(and
         (>= (first temps) low)
         (<= (first temps) high)
         (check-temps (rest temps) low high))
        true]
       [else false])]))
(check-expect (check-temps (cons 5(cons 95 empty)) 5 95)
              true)
(check-expect (check-temps (cons 5(cons 95 empty)) 6 95)
              false)
(check-expect (check-temps (cons 5(cons 95 empty)) 5 94)
              false)

;convert : (list) -> (list)
;Consumes a list of digits (numbers between 0 and 9) and
; produces the corresponding number. The first digit is
; the least significant, and so on.
(define (convert digits)
  (cond
    [(empty? digits)
     empty]
    [else
     (convert-helper digits empty)]))
(check-expect (convert empty) empty)
(check-expect (convert
               (cons 1 (cons 2 empty)))
              (cons 2 (cons 1 empty)) )
(check-expect (convert
               (cons 5 (cons 25 (cons 50 (cons 95 empty)))))
              (cons 95 (cons 50 (cons 25 (cons 5 empty)))))

;convert-helper : (list) (list) -> (list)
;Transfers the origional list of items to a new reversed list
(define (convert-helper orig new)
  (cond
    [(empty? orig)
     new]
    [else
     (convert-helper (rest orig) (cons (first orig) new))]))

;average-price : (listof number) -> number
;Consumes a list of toy prices and computes the average
; price of a toy. The average is total of all prices
; divided by the number of toys.
(define (average-price prices)
  (cond
    [(empty? prices)
     0]
    [(cons? prices)
     (/ (get-sum prices) (get-length prices))]))
(check-within (average-price (cons 15(cons 15 empty))) 15 .001)
(check-within (average-price (list 1 2)) 1.5 .001)
(check-expect (average-price empty) 0)

;get-sum : (listof number) -> number
;Consumes a list of numbers and returns their sum.
(define (get-sum numbers)
  (cond
    [(empty? numbers)
     0]
    [(cons? numbers)
     (+ (first numbers) (get-sum (rest numbers)))]))
(check-expect (get-sum (cons 1 (cons 2 empty)))
              3)
(check-expect (get-sum (cons 1 empty))
              1)

;get-length : (listof number) -> number
;Consumes a list and returns its length.
(define (get-length list)
  (cond
    [(empty? list)
     0]
    [(cons? list)
     (+ 1 (get-length (rest list)))]))
(check-expect (get-length (cons 1 (cons 2 empty)))
              2)
(check-expect (get-length (cons 2 empty))
              1)

;convertFC : (listof number) -> (listof number)
;Converts a list of Fahrenheit measurements to a list
; of Celsius measurements.
(define (convertFC fahrenheit)
  (cond
    [(empty? fahrenheit)
     empty]
    [(cons? fahrenheit)
     (cons
      (convert1FC (first fahrenheit))
      (convertFC (rest fahrenheit)))]))
(check-expect (convertFC (cons 50 empty))
              (cons (convert1FC 50) empty))
(check-expect (convertFC (cons 100 (cons 32 empty)))
              (cons
               (convert1FC 100)
               (cons 
                (convert1FC 32) 
                empty)))

;convert1FC : number -> number
;Converts a single Fahrenheit number to a Celsius number
(define (convert1FC fahrenheit)
  (* (- fahrenheit 32) (/ 5 9)))
(check-expect (convert1FC 32)
              0)
(check-expect (convert1FC 50)
              10)

;eliminate-exp : number (listof number) -> (listof number)
;Eliminates from lotp all toys whose price is greater than
; ua.
(define (eliminate-exp ua lotp)
  (cond
    [(empty? lotp)
     empty]
    [else
     (cond
       [(<= (first lotp) ua)
        (cons (first lotp) (eliminate-exp ua (rest lotp)))]
       [else
        (eliminate-exp ua (rest lotp))])]))
(check-expect (eliminate-exp 6 (list 3 5 7))
              (list 3 5))
(check-expect (eliminate-exp 7 (list 3 5 7))
              (list 3 5 7))
(check-expect (eliminate-exp 1 (list 3 5 7))
              empty)

;suffixes : list -> (listof list)
;Produces a list of suffixes of l.
(define (suffixes l)
  (cond
    [(empty? l)
     empty]
    [(cons? l)
     (cons
      l
      (suffixes (rest l)))]))
(check-expect (suffixes empty) empty)
(check-expect 
 (suffixes (cons 'a (cons 'b (cons 'c (cons 'd empty)))))
 (cons (cons 'a (cons 'b (cons 'c (cons 'd empty))))
       (cons (cons 'b (cons 'c (cons 'd empty)))
             (cons (cons 'c (cons 'd empty))
                   (cons (cons 'd empty) empty)))))

;unknown
;Represents an unknown ancestor.
(define-struct unknown ())

;person
;Represents a person.
(define-struct person (name birthyear eyecolor
                            father
                            mother))

;count-persons : (or unknown person) -> number
;Returns the number of people in a family tree.
(define (count-persons ftree)
  (if (person? ftree)
      (+ 1
         (if (person? (person-father ftree))
             (count-persons (person-father ftree))
             0)
         (if (person? (person-father ftree))
             (count-persons (person-mother ftree))
             0))
      0))
(check-expect (count-persons (make-unknown))
              0)
(check-expect (count-persons (make-person 
                              "fred" 1990 'blue
                              (make-person
                               "tom" 1970 'blue
                               (make-unknown)
                               (make-unknown))
                              (make-person
                               "marry" 1970 'blue
                               (make-unknown)
                               (make-unknown))))
              3)

;average-age : (or unknown person) -> number
;Returns the average age of all the people in the family
; tree.
(define (average-age ftree)
  (if 
   (person? ftree)
   (/ (count-age ftree) (count-persons ftree))
   0))
(check-expect (average-age (make-unknown))
              0)
(check-expect (average-age (make-person 
                            "fred" 1990 'blue
                            (make-person
                             "tom" 1970 'blue
                             (make-unknown)
                             (make-unknown))
                            (make-person
                             "marry" 1970 'blue
                             (make-unknown)
                             (make-unknown))))
              (/ (+ 43 43 23) 3))

;count-age : (or unknown person) -> number
;Returns the total number of years of everyone in the tree
(define (count-age ftree)
  (if (person? ftree)
      (+ (- 2013 (person-birthyear ftree))
         (if (person? (person-father ftree))
             (count-age (person-father ftree))
             0)
         (if (person? (person-father ftree))
             (count-age (person-mother ftree))
             0))
      0))
(check-expect (count-age (make-unknown)) 0)

;eye-colors : (or unknown person) -> (listof symbol)
;Produces a list of all eye colors in family tree.
(define (eye-colors ftree)
  (if
   (person? ftree)
   (cond
     [(and
       (person? (person-mother ftree))
       (person? (person-father ftree)))
      (cons (person-eyecolor ftree)
            (eye-colors(person-mother ftree)))]
     [(person? (person-mother ftree))
      (cons (person-eyecolor ftree)
            (eye-colors(person-mother ftree)))]
     [(person? (person-father ftree))
      (cons (person-eyecolor ftree)
            (eye-colors(person-father ftree)))]
     [else
      (cons (person-eyecolor ftree) empty)])
   empty))
(check-expect (eye-colors (make-unknown))
              empty)
(check-expect (eye-colors (make-person 
                           "fred" 1990 'blue
                           (make-person
                            "tom" 1970 'green
                            (make-unknown)
                            (make-unknown))
                           (make-person
                            "marry" 1970 'blue
                            (make-unknown)
                            (make-unknown))))
              (cons 'blue(cons 'blue empty)))
(check-expect (eye-colors (make-person 
                           "fred" 1990 'blue
                           (make-unknown)
                           (make-person
                            "marry" 1970 'blue
                            (make-unknown)
                            (make-unknown))))
              (cons 'blue(cons 'blue empty)))
(check-expect (eye-colors (make-person 
                           "fred" 1990 'blue
                           (make-person
                            "tom" 1970 'green
                            (make-unknown)
                            (make-unknown))
                           (make-unknown)))
              (cons 'blue(cons 'green empty)))