package chap05.decompile

object WrapperInner {
  implicit def imp(paramX: Int): OuterClass = new OuterClass(paramX) // 아우터 클래스는 부분 함수로 만들어 사용해야 한다.

  implicit class InnerClass(paramX: Int) { // 이너 클래스에는 implicit 키워드를 바로 사용 가능하다.
    println(s"InnerClass - $this")
    def ***(paramY: Int) = paramY * paramX
  }

}