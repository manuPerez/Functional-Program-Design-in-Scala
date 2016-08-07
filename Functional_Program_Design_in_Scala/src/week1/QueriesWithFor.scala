package week1

case class Book(title: String, authors: List[String])

/**
  * Created by manuelperez on 3/08/16.
  */
object QueriesWithFor extends App{
  val books: List[Book] = List(
    Book(title = "Structure and Interpretation of Computer Programs",
         authors = List("Abelson, Harald", "Sussman, Gerald J.")),
    Book(title = "Introduction to Functional Programming",
         authors = List("Bird, Richard", "Wadler, Phil")),
    Book(title = "Effective Java",
         authors = List("Bloch, Joshua")),
    Book(title = "Java Puzzlers",
         authors = List("Bloch, Joshua", "Gafter, Neal")),

    Book(title = "Prueba",
      authors = List("Bloch, Joshua", "Gafter, Neal")),


    Book(title = "Programming in Scala",
         authors = List("Odersky, Martin", "Spoon, Lex", "Venners, Bill")))

  // To find the titles of books whose author’s name is "Bird":
  val birds = for (
    b <- books;
    a <- b.authors if a startsWith "Bird,"
  ) yield b.title

  println(birds)

  // To find all the books which have the word "Program" in the title:
  val program = for (
    b <- books if b.title.indexOf("Program") >= 0
  ) yield b.title

  println(program)

  // To find the names of all authors who have written at least two
  // books present in the database.
  val authors = {for {
    b1 <- books // iterator ranging
    b2 <- books if b1.title < b2.title // iterator ranging
    a1 <- b1.authors
    a2 <- b2.authors if a1 == a2
  } yield a1}.distinct

  println(authors)
}
