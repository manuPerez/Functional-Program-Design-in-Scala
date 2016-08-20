package quickcheck

import common._
import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._

import scala.collection.immutable.Stream.Empty

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  lazy val genHeap: Gen[H] = for {
    a <- arbitrary[Int]
    h <- oneOf(const(empty), genHeap)
  } yield insert(a, h)

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

  property("gen1") = forAll { (h: H) =>
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  /**
    * If you insert any two elements into an empty heap,
    * finding the minimum of the resulting heap should get
    * the smallest of the two elements back.
    */
  property("min1") = forAll { (a: Int) =>
    val m = insert(a, empty)
    findMin(m) == a
  }

  /**
    * If you insert an element into an empty heap,
    * then delete the minimum, the resulting heap should be empty.
    */
  property("deleteMin1") = forAll { (a: Int) =>
    val m = insert(a, empty)
    deleteMin(m) == empty
  }

  /**
    * Given any heap, you should get a sorted sequence of elements
    * when continually finding and deleting minima.
    * (Hint: recursion and helper functions are your friends.)
    */
  property("ordering") = forAll { (h: H) =>
    def sorting(h1: H, li: List[Int]): List[Int] = {
      if (isEmpty(h1)) li else findMin(h1) :: sorting(deleteMin(h1), li)
    }
    val xs = sorting(h, Nil)
    xs == xs.sorted
  }

  /**
    * Finding a minimum of the melding of any two heaps
    * should return a minimum of one or the other.
    */
  property("melding") = forAll { (h1: H, h2: H) =>
    val h3 = meld(h1, h2)
    findMin(h3) == findMin(h2) || findMin(h3) == findMin(h1)
  }

  /**
    * If you insert any two elements into an empty heap,
    * finding the minimum of the resulting heap should get
    * the smallest of the two elements back.
    */
  property("insTwoElemInEmpty") = forAll { (a1: Int, a2: Int) =>
    val h = insert(a2, insert(a1, empty))
    val smallest = if (a1 < a2) a1 else a2
    findMin(h) == smallest
  }

  /** Take two arbitrary heaps, meld together.
    * Then remove min from 1 and insert into 2,
    * meld the results. Compare two melds by comparing sequences of ranks.
    */
  property("meldMinMove") = forAll { (h1: H, h2: H) =>
    def remMin(ts: H, as: List[Int]): List[Int] = {
      if (isEmpty(ts)) as
      else findMin(ts) :: remMin(deleteMin(ts), as)
    }
    val meld1 = meld(h1, h2)
    val min1 = findMin(h1)
    val meld2 = meld(deleteMin(h1), insert(min1, h2))
    val xs1 = remMin(meld1, Nil)
    val xs2 = remMin(meld2, Nil)
    xs1 == xs2
  }
}
