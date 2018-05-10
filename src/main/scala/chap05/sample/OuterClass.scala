package chap05.sample

class OuterClass(paramX: Int) {
  println(s"OuterClass - $this")
  def *&*(paramY: Int) = paramY * paramX
}