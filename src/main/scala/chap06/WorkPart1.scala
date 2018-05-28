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

  ////////////////////////////////////////////////////////////////////////////////////
  // #6.4 꼬리 호출과 꼬리 호출 최적화
  ////////////////////////////////////////////////////////////////////////////////////

  {
    import scala.annotation.tailrec

//    @tailrec
    def factorial(i: BigInt): BigInt = {
      if(i == 1) i
      else i * factorial(i - 1)
    }

    for(i <- 1 to 10)
      println(s"${i}:\t${factorial(i)}")
  }

  {
    // http://bozeury.tistory.com/entry/꼬리-재귀-최적화Tail-Recursion
    import scala.annotation.tailrec

    def factorial(i: BigInt): BigInt = {
      @tailrec
      def fact(i: BigInt, accumulator: BigInt) : BigInt =
        if(i == 1) accumulator
        else fact(i-1, i * accumulator)

      fact(i, 1)
    }

    for(i <- 1 to 10)
      println(s"${i}:\t${factorial(i)}")
  }

  {
    import scala.util.control.TailCalls._

    def isEven(xs: List[Int]): TailRec[Boolean] =
      if (xs.isEmpty) done(true) else tailcall(isOdd(xs.tail))

    def isOdd(xs: List[Int]): TailRec[Boolean] =
      if (xs.isEmpty) done(false) else tailcall(isEven(xs.tail))

    for (i <- 1 to 5) {
      val even = isEven((1 to i).toList).result
      println(s"$i is even? $even")
    }

  }

  ////////////////////////////////////////////////////////////////////////////////////
  // #6.5 부분 적용 함수와 부분 함수
  ////////////////////////////////////////////////////////////////////////////////////

  {
    def cat1(s1: String)(s2: String) = s1 + s2
    val hello = cat1("Hello ") _

    val p = hello("World")
    println(p)
  }

  ////////////////////////////////////////////////////////////////////////////////////
  // #6.6 함수의 커링과 다른 변환
  ////////////////////////////////////////////////////////////////////////////////////

  {
    def cat1(s1: String)(s2: String) = s1 + s2
    def cat2(s1: String) = (s2: String) => s1 + s2
    def cat3(s1: String, s2: String) = s1 + s2

    val cat3Curried = (cat3 _).curried
    // cat3Curried: String => (String => String) = <function1>

    cat3Curried("hello")("world")
    // helloworld

    cat3("hello", "world")
    // helloworld

    val cat3Uncurried = Function.uncurried(cat3Curried)
    // cat3Uncurried: (String, String) => String = <function2>

    cat3Uncurried("hello", "world")
    // helloworld
  }

  {
    val f1: String =>  String => String  = (s1: String) => (s2: String) => s1 + s2
    val f2: String => (String => String) = (s1: String) => (s2: String) => s1 + s2

    f1("hello")("world")
    // helloworld

    f2("hello")("world")
    // helloworld

    val ff1 = Function.uncurried(f1)
    val ff2 = Function.uncurried(f2)

    ff1("hello", "world")
    // helloworld

    ff2("hello", "world")
    // helloworld
  }

  {
    def mult(d1: Double, d2: Double, d3: Double) = d1 * d2 * d3

    val d3 = (2.2, 3.3, 4.4)

    mult(d3._1, d3._2, d3._3)

    val multTupled = Function.tupled(mult _)
    // multTupled: ((Double, Double, Double)) => Double = <function1>

    multTupled(d3)

    val multUntupled = Function.untupled(multTupled)

    multUntupled(d3._1, d3._2, d3._3)
  }

  {

    val finicky: PartialFunction[String,String] = {
      case "finicky" => "FINICKY"
    }

    val finickyOption = finicky.lift

    finicky("finicky")
    try {
      finicky("other")
    } catch {
      case e: scala.MatchError => e
    }

    finickyOption("finicky")
    finickyOption("other")

    val finicky2 = Function.unlift(finickyOption)

    finicky2("finicky")
    try {
      finicky2("other")
    } catch {
      case e: scala.MatchError => e
    }

  }

}
