package chap04

object WorkPart2 extends App {

  // 동반객체
  // http://partnerjun.tistory.com/11

  val d = Dog("Doberman")

  println(d.getName)
  d.bark

  ////////////////////////////////////////////////////////////////////////////////////
  // #4.6.1 unapply method
  ////////////////////////////////////////////////////////////////////////////////////

  case class With[A, B](a: A, b: B)

  val with1: With[String, Int] = With("Foo", 1)
  val with2: With[String, Int] = With("Bar", 2)

  Seq(with1, with2) foreach { w =>
    w match {
      case s With i => println(s"${s} with ${i}")
      case _        => println(s"Unknown: ${w}")
    }
  }

  /*
  val w = "one" With 2
  <console>:11: error: value With is not a member of String
    val w = "one" With 2
  */

  val nonEmptyList = List(1, 2, 3, 4, 5)
  val emptyList = Nil
  val nonEmptyMap = Map("one" -> 1, "two" -> 2, "three" -> 3)

  println(nonEmptyMap)
  println(nonEmptyMap.toSeq)

  def reverseSeqToString[T](l: Seq[T]): String = l match {
    case prefix :+ end => reverseSeqToString(prefix) + s":+ ${end}"
    case Nil => "Nil"
  }

  for(seq <- Seq(nonEmptyList, emptyList, nonEmptyMap.toSeq)){
    println(reverseSeqToString(seq))
  }

  /*
  scala> Nil:+ 1:+ 2:+ 3:+ 4:+ 5
  res0: List[Int] = List(1, 2, 3, 4, 5)
   */

  ////////////////////////////////////////////////////////////////////////////////////
  // #4.6.2 unapplySeq method
  ////////////////////////////////////////////////////////////////////////////////////

  def windows[T](seq: Seq[T]): String = seq match {
    case Seq(head1, head2, _*) =>
      s"(${head1}, ${head2}), " + windows(seq.tail)
    case Seq(head, _*) =>
      s"(${head}, _), " + windows(seq.tail)
    case Nil => "Nil"
  }

  for(seq <- Seq(nonEmptyList, emptyList, nonEmptyMap.toSeq)){
    println(windows(seq))
  }


  def windows2[T](seq: Seq[T]): String = seq match {
    case head1 +: head2 +: tail => s"(${head1}, ${head2}), " + windows(seq.tail)
    case head +: tail => s"(${head}, _), " + windows(seq.tail)
    case Nil => "Nil"
  }

  for(seq <- Seq(nonEmptyList, emptyList, nonEmptyMap.toSeq)){
    println(windows2(seq))
  }

  val seq = Seq(1, 2, 3, 4, 5)
  val slide2 = seq.sliding(2)

  println( slide2.toSeq )
  println( slide2.toList )

  println( seq.sliding(3, 2).toList )


  ////////////////////////////////////////////////////////////////////////////////////
  // #4.7 가변 인자 목록과 일치시키기
  ////////////////////////////////////////////////////////////////////////////////////

  object Op extends Enumeration {
    type Op = Value // type alias

    val EQ = Value("=")
    val NE = Value("!=")
    val LTGT = Value("<>")
    val LT = Value("<")
    val LE = Value("<=")
    val GT = Value(">")
    val GE = Value(">=")
  }

  import Op._

  case class WhereOp[T](columnName: String, op: Op, value: T)
  case class WhereIn[T](columnName: String, val1: T, vals: T*)

  val wheres = Seq(
    WhereIn("state", "IL", "CA", "VA"),
    WhereOp("state", EQ, "IL"),
    WhereOp("name", EQ, "Buck Trends"),
    WhereOp("age", GT, 29)
  )

  for(where <- wheres) {
    where match {
      case WhereIn(col, val1, vals @ _*) =>
        val valStr = (val1 +: vals).mkString(", ")
        println(s"WHERE ${col} IN (${valStr})")
      case WhereOp(col, op, value) => println(s"WHERE ${col} ${op} ${value}")
      case _ => println(s"ERROR: Unknown expression: ${where}")
    }
  }

  ////////////////////////////////////////////////////////////////////////////////////
  // #4.8 정규 표현식과 일치시키기
  ////////////////////////////////////////////////////////////////////////////////////

  val BookExtractorRE = """Book: title=([^,]+),\s+author=(.+)""".r
  val MagazineExtractorRE = """Magazine: title=([^,]+),\s+issue=(.+)""".r

  val BookExtractorRE(title, author) = "Book: title=Programming Scala Second Edition, author=Dean Wampler"
  println(s"${title} => ${author}")

  val catalog = Seq(
    "Book: title=Programming Scala Second Edition, author=Dean Wampler",
    "Magazine: title=The New Yorker, issue=January 2014",
    "Unknown: text=Who put this here??"
  )

  for(item <- catalog) {
    item match {
      case BookExtractorRE(title, author) => println(s"""Book "${title}", written by ${author} """)
      case MagazineExtractorRE(title, issue) => println(s"""Magazine "${title}", issue by ${issue} """)
      case entry => println(s"Unrecognized entry: ${entry}")
    }
  }

  ////////////////////////////////////////////////////////////////////////////////////
  // #4.9 케이스 절의 변수 바인딩에 대해 더 살펴보기
  ////////////////////////////////////////////////////////////////////////////////////

  case class Address(street: String, city: String, country: String)
  case class Person(name: String, age: Int, address: Address)

  val alice   = Person("Alice",   25, Address("1 Scala Lane", "Chicago", "USA"))
  val bob     = Person("Bob",     29, Address("2 Java Ave.",  "Miami",   "USA"))
  val charlie = Person("Charlie", 32, Address("3 Python Ct.", "Boston",  "USA"))

  for(person <- Seq(alice, bob, charlie)) {
    person match {
      case p @ Person("Alice", 25, address) => println(s"Hi Alice! ${p}")
      case p @ Person("Bob", 29, a @ Address(street, city, country)) =>
        println(s"Hi ${p.name}! age ${p.age}, in ${a.city}")
      case p @ Person(name, age, _) =>
        println(s"Who are you, ${age} year-old person named ${name}? ${p}")
    }
  }

  ////////////////////////////////////////////////////////////////////////////////////
  // #4.10 타입 일치에 대해 더 살펴보기
  ////////////////////////////////////////////////////////////////////////////////////

  val rs = for {
    x <- Seq(List(5.5,5.6,5.7), List("a", "b"))
  } yield (x match {
    case seqd: Seq[Double] => ("seq double", seqd)
    case seqs: Seq[String] => ("seq string", seqs)
    case _                 => ("unknown!", x)
  })

  println(rs)

  // ==>
  def doSeqMatch[T](seq: Seq[T]): String = seq match {
    case Nil => "Nothing"
    case head +: _ => head match {
      case _ : Double => "Double"
      case _ : String => "String"
      case _ => "Unmatched seq element"
    }
  }

  val dsm = for {
    x <- Seq(List(5.5,5.6,5.7), List("a", "b"))
  } yield {
    x match {
      case seq: Seq[_] => (s"seq ${doSeqMatch(seq)}", seq)
      case _           => ("unknown!", x)
    }
  }

  println(dsm)

  ////////////////////////////////////////////////////////////////////////////////////
  // #4.11 봉인된 클래스 계층과 매치의 완전성
  ////////////////////////////////////////////////////////////////////////////////////

  import chap04.Http._

  val methods = Seq(
    Connect("connect body..."),
    Delete("delete body..."),
    Get("get body..."),
    Head("head body..."),
    Options("options body..."),
    Post("post body..."),
    Put("put body..."),
    Trace("trace body...")
  )

  methods foreach(method => println(handle(method)))

  ////////////////////////////////////////////////////////////////////////////////////
  // #4.12 패턴 매칭의 다른 사용법
  ////////////////////////////////////////////////////////////////////////////////////

  val Person(name, age, Address(_, state, _)) = Person("Dean", 29, Address("1 Scala Way", "CA", "USA"))
  println(s"${name} (${age}) : ${state}")


  case class Person2(name: String, age: Int)

  val as = Seq(
    Address("1 Scala Lane", "Anytown", "USA"),
    Address("2 Clojure Lane", "Othertown", "USA"))
  val ps = Seq(
    Person2("Buck Trends", 29),
    Person2("Clo Jure", 28))

  val pas = ps zip as

  // Ugly way:
  pas map { tup =>
    val Person2(name, age) = tup._1
    val Address(street, city, country) = tup._2
    s"$name (age: $age) lives at $street, $city, in $country"
  }

  // Nicer way:
  pas map {
    case (Person2(name, age), Address(street, city, country)) =>
      s"$name (age: $age) lives at $street, $city, in $country"
  }
}