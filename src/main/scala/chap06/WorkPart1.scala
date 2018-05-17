package chap06

object WorkPart1 extends App {

  ////////////////////////////////////////////////////////////////////////////////////
  // #6.2 스칼라 함수형 프로그래밍
  ////////////////////////////////////////////////////////////////////////////////////
  {
    val f1 = ( 1 to 10 ) filter (_ % 2 == 0) map (_ * 2) reduce (_ * _)
    println(f1)
  }

  ////////////////////////////////////////////////////////////////////////////////////
  // #6.2.1 익명 함수, 람다, 클로저
  ////////////////////////////////////////////////////////////////////////////////////
  {
    var factor = 2
    val multiplier = (i: Int) => i * factor

    val f1 = (1 to 10) filter (_ % 2 == 0) map multiplier reduce(_ * _)
    println(f1)

    factor = 3
    val f2 = (1 to 10) filter (_ % 2 == 0) map multiplier reduce(_ * _)
    println(f2)
  }

  // 1급 객체 https://medium.com/@lazysoul/functional-programming-에서-1급-객체란-ba1aeb048059
  // https://github.com/funfunStudy/study/wiki/8장-함수와-클로저

  {
    def m1(multiplier: Int => Int) = {
      (1 to 10) filter (_ % 2 == 0) map multiplier reduce(_ * _)
    }

    def m2: Int => Int = {
      val factor = 2
      val multiplier = (i: Int) => i * factor
      multiplier
    }

    println( m1(m2) )
  }

  {
    object Multiplier {
      var factor = 2

//      val multiplier = (i: Int) => i * factor

      def multiplier(i: Int) = i * factor
    }

    val f1 = (1 to 10) filter (_ % 2 == 0) map Multiplier.multiplier reduce(_ * _)
    println(f1)

    Multiplier.factor = 3
    val f2 = (1 to 10) filter (_ % 2 == 0) map Multiplier.multiplier reduce(_ * _)
    println(f2)
  }
}
