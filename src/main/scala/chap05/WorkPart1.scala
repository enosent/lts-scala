package chap05

object WorkPart1 extends App{

  ////////////////////////////////////////////////////////////////////////////////////
  // #5.1 암시적 인자
  ////////////////////////////////////////////////////////////////////////////////////

  def calcTax(amount: Float)(implicit rate: Float): Float = amount * rate

  object SimpleStateSalesTax {
    implicit val rate: Float = 0.05F
  }

  case class ComplicatedSalesTaxData(baseRate: Float, isTaxHoliday: Boolean, storeId: Int)

  object ComplicatedSalesTax {
    private def extraTaxRateForStore(id: Int): Float = {
      0.0F
    }

    implicit def rate(implicit cstd: ComplicatedSalesTaxData): Float =
      if(cstd.isTaxHoliday) 0.0F
      else cstd.baseRate + extraTaxRateForStore(cstd.storeId)
  }

  {
    import SimpleStateSalesTax.rate

    val amount = 100F
    println(s"Tax on ${amount} = ${calcTax(amount)}")
  }

//  println(calcTax(100))

  {
    import ComplicatedSalesTax.rate

    implicit val myStore = ComplicatedSalesTaxData(0.06F, false, 1010)

    val amount = 100F
    println(s"Tax on ${amount} = ${calcTax(amount)}")
  }

  val f = calcTax(100)(0.05F) // currying http://hamait.tistory.com/693
  println(f)

  ////////////////////////////////////////////////////////////////////////////////////
  // #5.1.1 implicitly 사용하기
  ////////////////////////////////////////////////////////////////////////////////////

  {
    import math.Ordering

    case class MyList[A](list: List[A]) {
      def sortBy1[B](f: A => B)(implicit ord: Ordering[B]): List[A] ={
//        println( f(list(0)) )

        list.sortBy(f)(ord)
      }

      def sortBy2[B: Ordering](f: A => B): List[A] = list.sortBy(f)(implicitly[Ordering[B]])
    }

    val list = MyList(List(1,3,5,2,4))

    list.sortBy1( i => -i ) foreach println
    list.sortBy2( i => -i ) foreach println
  }

  {

    def sum(fv: Int => Int) : Int = {
      var total = 0
      for (i <- 1 to 10) {
        total += fv(i)
      }
      total
    }

    println(sum(a => a))
    println(sum(a => a*2))
  }

  {
    // 익명 함수
    // https://docs.scala-lang.org/ko/tutorials/tour/anonymous-function-syntax.html
    /*
    (x: Int) => x + 1

    new Function1[Int, Int] {
      def apply(x: Int): Int = x + 1
    }
    */


    val f: Function1[Int,String] = myInt => "my int: "+myInt.toString

    println( f(0) )

    val f2: Int => String = myInt => "my int v2: "+myInt.toString

    println( f2(0) )

    val ff: Function0[Unit] = () => println("x2")

    ff()
    println( ff )

    val ff2: () => Unit = () => { println("x")}

    ff2()
    println( ff2 )
  }

  ////////////////////////////////////////////////////////////////////////////////////
  // #5.2.2 사용 가능한 기능 제어하기
  ////////////////////////////////////////////////////////////////////////////////////

  {
    // 공변성 : http://hamait.tistory.com/567
  }

  ////////////////////////////////////////////////////////////////////////////////////
  // #5.2.3 사용 가능한 인스턴스 제한하기
  ////////////////////////////////////////////////////////////////////////////////////
  {
    // ScalaDatabaseApi$ / JavaDatabaseApi.scala / DBRow.scala
  }

  ////////////////////////////////////////////////////////////////////////////////////
  // #5.2.4 암시적 증거 제공하기
  ////////////////////////////////////////////////////////////////////////////////////
  {
    import scala.collection.TraversableOnce
  }

  ////////////////////////////////////////////////////////////////////////////////////
  // #5.2.5 타입 소거 우회하기
  ////////////////////////////////////////////////////////////////////////////////////
  {
    /*
    object M {
      def m(seq: Seq[Int]): Unit = println(s"Seq[Int]: ${seq}")
      def m(seq: Seq[String]): Unit = println(s"Seq[String]: ${seq}")
    }
    */

    object M {
      implicit object IntChecker
      implicit object StrChecker

      def m(seq: Seq[Int])(implicit i: IntChecker.type): Unit = println(s"Seq[Int]: ${seq}")
      def m(seq: Seq[String])(implicit s: StrChecker.type): Unit = println(s"Seq[String]: ${seq}")
    }

    import M._
    m(List(1,2,3))
    m(List("one", "two", "three"))
  }


  ////////////////////////////////////////////////////////////////////////////////////
  // #5.2.6 오류 메시지 개선하기
  ////////////////////////////////////////////////////////////////////////////////////
  {
    import scala.collection.generic.CanBuildFrom
  }

  ////////////////////////////////////////////////////////////////////////////////////
  // #5.2.7 유령 타입
  ////////////////////////////////////////////////////////////////////////////////////
  {
    // CalculatePayroll.scala / CalculatePayroll2$ / Pipeline$
  }

}
