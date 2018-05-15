package chap05.decompile

class OuterClass(paramX: Int) {
  println(s"OuterClass - $this")
  def *&*(paramY: Int) = paramY * paramX
}