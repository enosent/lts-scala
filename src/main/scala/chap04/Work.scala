package chap04

object Work extends App {
  ////////////////////////////////////////////////////////////////////////////////////
  // #4.1 단순 매칭
  ////////////////////////////////////////////////////////////////////////////////////

  val bools = Seq(true, false)

  for(bool <- bools) {
    bool match {
      case true => println("Got heads")
      case false => println("Got tails")
    }
  }

  for(bool <- bools) {
    val which = if(bool) "heads" else "tails"
    println(s"Got ${which}")
  }

  ////////////////////////////////////////////////////////////////////////////////////
  // #4.2 매치 내의 값, 변수, 타입
  ////////////////////////////////////////////////////////////////////////////////////

  // http://partnerjun.tistory.com/48
  val f = 'four
  println(f.getClass.getName) // Symbol type

  for {
    x <- Seq(1, 2, 2.7, "one", "two", 'four)
  } {
    val str = x match {
      case 1 => "int 1"
      case i: Int => s"other int: ${i}"
      case d: Double => s"a double ${d}"
      case "one" => "string one"
      case s: String => s"other string: ${s}"
      case unexpected => s"unexpected value: ${unexpected}"
    }
    println(str)
  }

  for {
    x <- Seq(1, 2, 2.7, "one", "two", 'four)
  } {
    val str = x match {
      case 1 => "int 1"
      case _: Int => s"other int: ${x}"
      case _: Double => s"a double ${x}"
      case "one" => "string one"
      case _: String => s"other string: ${x}"
      case _ => s"unexpected value: ${x}"
    }
    println(str)
  }

  for {
    x <- Seq(1, 2, 2.7, "one", "two", 'four)
  } {
    val str = x match {
      case _: Int | _: Double => s"a number: ${x}"
      case "one" => "string one"
      case _: String => s"other string: ${x}"
      case _ => s"unexpected value: ${x}"
    }
    println(str)
  }

  def checkY(y: Int) = {
    for {
      x <- Seq(99, 100, 101)
    } {
      val str = x match {
        case `y` => "found y!"
        case i: Int => s"int: ${i}"
      }
      println(str )
    }
  }

  checkY(100)

  ////////////////////////////////////////////////////////////////////////////////////
  // #4.3 시퀀스에 일치시키기
  ////////////////////////////////////////////////////////////////////////////////////

  val aa = "head"
  val bb = "tail"

  println( aa +: bb )

//  println( aa :: bb )

  val tt = Seq(1,2,3,4,5)
  val tt_head = tt.head
  val tt_tail = tt.tail

  println( tt_head )
  println( tt_tail )
  println( tt_head +: tt_tail )

  /*
  def seqToString(seq: Seq[Int]): String = seq match {
    case head +: tail => s"$head +: ${seqToString(tail)}"
    case Nil => "Nil"
  }

  println(seqToString(Seq(1,2,3,4,5)))
  */

  val nonEmptySeq = Seq(1, 2, 3, 4, 5)
  val emptySeq = Seq.empty[Int]
  val nonEmptyList = List(1, 2, 3, 4, 5)
  val emptyList = Nil
  val nonEmptyVector = Vector(1, 2, 3, 4, 5)
  val emptyVector = Vector.empty[Int]
  val nonEmptyMap = Map("one" -> 1, "two" -> 2, "three" -> 3)
  val emptyMap = Map.empty[String, Int]


  def seqToString[T](seq: Seq[T]): String = seq match {
    case head +: tail => s"$head +: ${seqToString(tail)}"
    case Nil => "Nil"
  }

  for(seq <- Seq(
    nonEmptySeq, emptySeq, nonEmptyList, emptyList,
    nonEmptyVector, emptyVector, nonEmptyMap.toSeq, emptyMap.toSeq
  )) {
    println( seqToString(seq) )
  }

  def seqToString2[T](seq: Seq[T]): String = seq match {
    case h +: t =>  s"(${h} +: ${seqToString2(t)})"
    case Nil => "(Nil)"
  }

  for(seq <- Seq(nonEmptySeq, emptySeq, nonEmptyMap.toSeq)) {
    println( seqToString2(seq) )
  }

  def listToString[T](list: List[T]): String = list match {
    case head :: tail => s"(${head} :: ${listToString(tail)})"
    case Nil => "(Nil)"
  }

  for(l <- List(nonEmptyList, emptyList)) { println(listToString(l)) }

//  println( 1 +: 2 )
  println( 1 +: Nil )

  val s1 = (1 +: (2 +: (3 +: (4 +: (5 +: Nil)))))
  println( s1 )

  val l = (1 :: (2 :: (3 :: (4 :: (5 :: Nil)))))
  println( l )

  val s2 = (("one", 1) +: ("two", 2) +: ("three", 3) +: Nil )
  println( s2 )

  val m = Map(s2: _*)
  println(m)


  def sum(xs:Int*):Int = if (xs.isEmpty) 0 else xs.head + sum(xs.tail:_*)

  val r1 = sum()                   // 0
  println(r1)
  val r2 = sum(1)             // 1
  println(r2)
  val r3 = sum(1, 2, 3)       // 6
  println(r3)

  val ns = List(1, 3, 5)
  val r4 = sum(ns:_*)         // 9
  println(r4)

//  sum(List(1, 3, 5))

  ////////////////////////////////////////////////////////////////////////////////////
  // #4.4 튜플에 일치시키기
  ////////////////////////////////////////////////////////////////////////////////////

  val langs = Seq(
    ("Scala", "Martin", "Odersky"),
    ("Clojure", "Rich", "Hickey"),
    ("Lisp", "John", "McCarthy")
  )

  for(tuple <- langs) {
    tuple match {
      case ("Scala", _, _) => println("Found Scala")
      case (lang, first, last) =>
        println(s"Found ohter language: ${lang} (${first} ${last})")
    }
  }

  ////////////////////////////////////////////////////////////////////////////////////
  // #4.5 케이스 절의 가드
  ////////////////////////////////////////////////////////////////////////////////////

  for(i <- Seq(1,2,3,4)) {
    i match {
      case _ if i%2 == 0 => println(s"even: ${i}")
      case _             => println(s"odd: ${i}")
    }
  }

  ////////////////////////////////////////////////////////////////////////////////////
  // #4.6 케이스 클래스에 일치시키기
  ////////////////////////////////////////////////////////////////////////////////////

  case class Address(street: String, city: String, country: String)
  case class Person(name: String, age: Int, adress: Address)

  val alice   = Person("Alice",   25, Address("1 Scala Lane", "Chicago", "USA"))
  val bob     = Person("Bob",     29, Address("2 Java Ave.",  "Miami",   "USA"))
  val charlie = Person("Charlie", 32, Address("3 Python Ct.", "Boston",  "USA"))

  for(person <- Seq(alice, bob, charlie)) {
    person match {
      case Person("Alice", 25, Address(_, "Chicago", _)) => println("Hi Alice!")
      case Person("Bob", 29, Address("2 Java Ave.",  "Miami",   "USA")) => println("Hi Bob!")
      case Person(name, age, _) =>
        println(s"Who are you, ${age} year-old person named ${name}?")
    }
  }

  val itemsCosts = Seq(("Pencil", 0.52), ("Paper", 1.35), ("Notebook", 2.43))
  val itemsCostsIndices = itemsCosts.zipWithIndex

  for(itemCostIndex <- itemsCostsIndices) {
    itemCostIndex match {
      case ((item, cost), index) => println(s"${index}: ${item} costs ${cost} each")
    }
  }
}
