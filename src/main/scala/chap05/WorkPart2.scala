package chap05

import chap05.config.Pp._

object WorkPart2 extends App {

  ////////////////////////////////////////////////////////////////////////////////////
  // #5.3 암시적 변환
  ////////////////////////////////////////////////////////////////////////////////////
  {
    import scala.language.implicitConversions

    case class Foo(s: String)
    object Foo {
      implicit def fromString(s: String): Foo = Foo(s)
    }

    class O {
      def m1(foo: Foo) = println(foo)
      def m(s: String) = m1(s)
    }



    new O().m("hello")

    implicit def overridingConversion(s: String): Foo = Foo(s"Boo: ${s}")
  }


  ////////////////////////////////////////////////////////////////////////////////////
  // #5.3,1 자신만의 문자열 인터폴레이션 만들기
  ////////////////////////////////////////////////////////////////////////////////////

  {
    val s = "Programming Scala"

    s.reverse
    s.capitalize

    s.foreach(c => print(s"${c} -"))
    println
  }

  {
    val name = ("Buck", "Trends")

    println(s"Hello, ${name._1} ${name._2}")

    StringContext("Hello, ", " ", "").s(name._1, name._2)
  }



  {
    import chap05.`implicit`.JsonParserTest._

    val name = "Dean Wampler"
    val book = "Programming Scala, Second Edition"
    val jsonobj = jsonParser"{name: $name, book: $book}"

    println(jsonobj)
  }


  ////////////////////////////////////////////////////////////////////////////////////
  // #5.4 타입 클래스 패턴
  ////////////////////////////////////////////////////////////////////////////////////
  {
    import chap05.`implicit`.ToJson._

    val a = Address("1 Scala Lane", "Anytown")
    val p = Person("Buck Trends", a)

    println(a.toJSON())
    println()
    println(p.toJSON())

    1 → 2
  }

  println(1 ♡ 2)

}
