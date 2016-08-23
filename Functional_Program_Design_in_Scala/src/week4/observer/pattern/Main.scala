package week4.observer.pattern

object Main {

  def main(args: Array[String]): Unit = {
    val a = new BankAccount
    val b = new BankAccount
    val c = new Consolidator(List(a, b))

    println(c.totalBalance)

    a deposit(20)
    println(c.totalBalance)

    b deposit(30)
    println(c.totalBalance)
  }

}
