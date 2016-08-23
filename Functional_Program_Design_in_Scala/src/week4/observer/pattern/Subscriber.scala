package week4.observer.pattern

trait Subscriber {
  def handler(pub: Publisher)
}
