package chap04

class Dog(name: String) {
  def bark = println("bark! bark!")
  def getName = name
}

object Dog {
  def apply(name: String) = new Dog(name)
}