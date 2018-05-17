package chap05.config

object Pp {
  implicit class toPiped(val a1: Int) {
    def ♡(a: Int): Int =  a + a1
  }
}

