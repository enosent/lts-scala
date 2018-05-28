package chap06

object WorkPart2 extends App {

  ////////////////////////////////////////////////////////////////////////////////////
  // #6.7.1 시퀀스
  ////////////////////////////////////////////////////////////////////////////////////
  {
    val a = List(1, 2, 3, 4, 5)

    println(a.head)
    println(a.tail)

    val list1 = List("Programming", "Scala")
    val list2 = "People" :: "should" :: "read" :: list1

    println(list2)

    println(Nil) // List.empty[Nothing]

    val list3 = "Programming" :: "Scala" :: Nil
    println(list3)

    val list4 = "People" :: "should" :: "read" :: Nil
    println(list4)

    val list5 = list4 ++ list3
    println(list5)
  }

  {
    // Seq
    val seq1 = Seq("Programming", "Scala")
    println( seq1 )

    val seq2 = "People" +: "should" +: "read" +: seq1
    println( seq2 )

    val seq3 = "Programming" +: "Scala" +: Seq.empty
    println( seq3 )

    val seq4 = "People" +: "should" +: "read" +: Seq.empty
    println( seq4 )

    val seq5 = seq4 ++ seq3
    println( seq5 )

    val rSeq1 = Seq("Programming", "Scala")
    val rSeq2 = rSeq1 :+ "Rocks!"
    println( rSeq2 )
  }

  {
    // Vector
    val vec1 = Vector("Programming", "Scala")
    println( vec1 )

    val vec2 = "People" +: "should" +: "read" +: vec1
    println( vec2 )

    val vec3 = "Programming" +: "Scala" +: Vector.empty
    println( vec3 )

    val vec4 = "People" +: "should" +: "read" +: Vector.empty
    println( vec4 )

    val vec5 = vec4 ++ vec3
    println( vec5 )

    println( vec5(3) )
  }

  ////////////////////////////////////////////////////////////////////////////////////
  // #6.7.2 맵
  ////////////////////////////////////////////////////////////////////////////////////
  {
    val stateCapitals = Map(
      "Alabama" -> "Montgomery",
      "Alaska"  -> "Juneau",
      "Wyoming" -> "Cheyenne")

    val lengths = stateCapitals map {
      kv => (kv._1, kv._2.length)
    }
    println(lengths)

    val caps = stateCapitals map {
      case (k, v) => (k, v.toUpperCase)
    }
    println(caps)

    val stateCapitals2 = stateCapitals + ("Virginia" -> "Richmond")
    println(stateCapitals2)

    val stateCapitals3 = stateCapitals2 + ("New York" -> "Albany", "Illinois" -> "Springfield")
    println(stateCapitals3)

  }

  ////////////////////////////////////////////////////////////////////////////////////
  // #6.7.3 집합
  ////////////////////////////////////////////////////////////////////////////////////
  {
    val states = Set("Alabama", "Alaska", "Wyoming")

    val lengths = states map (st => st.length)
    println(lengths)

    val states2 = states + "Virginia"
    println(states2)

    val states3 = states2 + ("New York", "Illinois")
    println(states3)
  }

  ////////////////////////////////////////////////////////////////////////////////////
  // #6.8.1 순회
  ////////////////////////////////////////////////////////////////////////////////////
  {
    List(1, 2, 3, 4, 5) foreach { i => println("Int: " + i) }

    val stateCapitals = Map(
      "Alabama" -> "Montgomery",
      "Alaska"  -> "Juneau",
      "Wyoming" -> "Cheyenne")

    //stateCapitals foreach { kv => println(kv._1 + ": " + kv._2) }
    stateCapitals foreach { case (k, v) => println(k + ": " + v) }
  }

  ////////////////////////////////////////////////////////////////////////////////////
  // #6.8.2 연관시키기
  ////////////////////////////////////////////////////////////////////////////////////
  {
    object Combinators1 {
      def map[A,B](list: List[A])(f: (A) ⇒ B): List[B] = list map f
    }

    object Combinators {
      def map[A,B](f: (A) ⇒ B)(list: List[A]): List[B] = list map f
    }

    val intToString = (i:Int) => s"N=$i"
    // Result: intToString: Int => String = <function1>

    val flist = Combinators.map(intToString) _
    // Result: flist: List[Int] => List[String] = <function1>

    val list = flist(List(1, 2, 3, 4))
    // Result: list: List[String] = List(N=1, N=2, N=3, N=4)

    println( list )

    val flist1: (Int => String) => List[String] = Combinators1.map(List(1, 2, 3, 4)) _

    println( flist1(intToString) )
  }

  ////////////////////////////////////////////////////////////////////////////////////
  // #6.8.3 펼치면서 연관시키기
  ////////////////////////////////////////////////////////////////////////////////////
  {
    val list = List("now", "is", "", "the", "time")

    println( list flatMap(s => s.toList) )

    println( "now".toList )

    val list2 = List("now", "is", "", "the", "time") map ( s => s.toList )

    println( list2.flatten )
  }

}
