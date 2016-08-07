// Simplify combinations of core methods map , flatMap , filter .

val n = 7

def isPrime(num: Int): Boolean = {
  (for(
    a <- 2 until num -1
  ) yield (num % a)) forall (b => b != 0)
}

(1 until n) flatMap (i =>
  (1 until i) map (j => (i, j))) filter (pair =>
  isPrime(pair._1 + pair._2))

// or with for expression
for{
  i <- 1 until n
  j <- 1 until i
  if isPrime(i + j)
} yield (i, j)


// 1. A simple for expression

for(
  x <- e1 // generator
) yield e2

// is translated to

e1.map(x => e2)

// 2. A for expression

for(
  x <- e1 if f
  s
) yield e2

// where f is a filter and s is a (potentially empty) sequence of
// generators and filters, is translated to

for(
  x <- e1.withFilter(x => f)
  s
) yield e2


// 3. A for expression

for(
  x <- e1  // generator
  y <- e2  // generator
  s
) yield e3

// is translated to

e1.flatMap(x => (for(
                    y <- e2 // generator
                    s
                  ) yield e3
                ))

// and

e1.flatMap(x => (
  e2 map (y => e3
  )
)
