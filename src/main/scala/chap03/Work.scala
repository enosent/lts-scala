package chap03

object Work extends App {

  println( 1 + 2 * 3 )
  println( (1.+(2)).*(3) )

  ////////////////////////////////////////////////////////////////////////////////////
  // #1 메소드 호출
  ////////////////////////////////////////////////////////////////////////////////////

  def isEven(n: Int) = (n % 2) == 0

  List(1, 2, 3, 4) filter isEven foreach println

  /*
  List(1, 2, 3, 4).filter((i : Int) => isEven(i)).foreach((i: Int) => println(i))
  List(1, 2, 3, 4).filter(i => isEven(i)).foreach(i => println(i))

  List(1, 2, 3, 4).filter(isEven(_)).foreach(println(_))

  List(1, 2, 3, 4).filter(isEven).foreach(println)
  */


  ////////////////////////////////////////////////////////////////////////////////////
  // #2 우선순위
  ////////////////////////////////////////////////////////////////////////////////////

  println( 2.0 * 4.0 / 3.0 * 5.0 )
  // ==========================>>>
  // (((2.0 * 4.0) / 3.00 ) * 5.0)
  //  2.0.*(4.0)./(3.0).*(5.0)

  // `:` 끝나는 메소드는 반대 ( cons )

  val list = List('b', 'c', 'd')

  val consList = 'a' :: list  // list.::('a') 'a'를 배열에 추가

  println( consList )

  val mergeList = list ::: List('a') // 두개의 리스트를 하나로 합침

  println( mergeList )

  /*
  val a = List('a')

  a.::(list)
  list.::(a)
  */


  ////////////////////////////////////////////////////////////////////////////////////
  // #3 if문
  ////////////////////////////////////////////////////////////////////////////////////

  if(2 + 2 == 5) {
    println("Hello form 1984.")
  } else if(2 + 2 == 3) {
    println("Hello from Remedial Math class?")
  } else {
    println("Hello from a non-Orwellian future.")
  }

  // 변수로 할당 가능
  val path =
    if(2 + 2 == 3) {
      System.getenv("PATH")
    } else {
      System.getenv("USER")
    }

  println(path)

  ////////////////////////////////////////////////////////////////////////////////////
  // #4 for 문
  ////////////////////////////////////////////////////////////////////////////////////

  val dogBreeds = List("Doberman", "Yorkshire Terrier", "Dachshund",
    "Scottish Terrier", "Great Dane", "Portuguese Water Dog")

  for(breed <- dogBreeds)
    println(breed)

  for(i <- 1 to 10) println(i)
  for(i <- 1 until 10) println(i)

  for(breed <- dogBreeds if breed.contains("Terrier")) println(breed)

  for(breed <- dogBreeds
      if breed.contains("Terrier")
      if !breed.startsWith("Yorkshire"))
    println(breed)


  for(breed <- dogBreeds
      if breed.contains("Terrier") && !breed.startsWith("Yorkshire"))
    println(breed)



  val filteredBreeds = for(breed <- dogBreeds
                           if breed.contains("Terrier") && !breed.startsWith("Yorkshire")) yield breed

  /*
  val p = for(breed <- dogBreeds
      if breed.contains("Terrier") && !breed.startsWith("Yorkshire")) breed

  println(p)
  */

  for {
    breed <- dogBreeds
    upCasedBreed = breed.toUpperCase()
  } println(upCasedBreed)



  val dogBreeds2 = List(Some("Doberman"), Some("Yorkshire Terrier"), Some("Dachshund"),
    Some("Scottish Terrier"), Some("Great Dane"), None, Some("Portuguese Water Dog"))

  println("first pass:")
  for {
    breedOption <- dogBreeds2
    breed <- breedOption
    upCasedBreed = breed.toUpperCase()
  } println(upCasedBreed)

  println("second pass:")
  for {
    Some(breed) <- dogBreeds2
    upCasedBreed = breed.toUpperCase()
  } println(upCasedBreed)

  for( breed <- dogBreeds2 )
    println(breed.getOrElse("B"))


  ////////////////////////////////////////////////////////////////////////////////////
  // #4 while 문
  ////////////////////////////////////////////////////////////////////////////////////

  import java.util.Calendar

  import Fruits.Value

  def isFridayThirteen(cal: Calendar) : Boolean = {
    val dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)
    val dayOfMonth = cal.get(Calendar.DAY_OF_MONTH)

    ( dayOfWeek == Calendar.FRIDAY) && (dayOfMonth == 13)
  }

  /*
  while( !isFridayThirteen(Calendar.getInstance())) {
    println("Today isn't Friday the 13th. Lame.")
  }
  */

  var count = 0
  do {
    count += 1
    println(count)
  } while(count < 10)

  ////////////////////////////////////////////////////////////////////////////////////
  // #5 try-catch 문
  ////////////////////////////////////////////////////////////////////////////////////

  import scala.util.{Failure, Success, Try}

  val throwException = Try {
    //  println("A".toLong)
    "1".toLong
  } match {
    case Success(result) => result
    case Failure(exception) => exception
  }

  println(throwException)

  // http://knight76.tistory.com/entry/scala-trycatchTrymatchEither
  // https://www.programcreek.com/scala/scala.util.Try

  ////////////////////////////////////////////////////////////////////////////////////
  // #6 call-by-name / call-by-value
  ////////////////////////////////////////////////////////////////////////////////////

  def doSomething(): Int = {
    println("doSomething ... ")
    1
  }

  def callByValue(x: Int) = {
    println(s"x1 : ${x} ")
    println(s"x2 : ${x} ")
  }

  def callByName(x: => Int) = {
    println(s"x1 : ${x} ")
    println(s"x2 : ${x} ")
  }

  callByName(doSomething())
  callByValue(doSomething())


  ////////////////////////////////////////////////////////////////////////////////////
  // #7 enum
  ////////////////////////////////////////////////////////////////////////////////////
  object Fruits extends Enumeration {
    val Apple = Value
    val Banana = Value(5)
    val Orange = Value("orange")
  }

  val apple = Fruits.withName("Apple")

  println(apple)
  println(apple.id)

  implicit class EnumsMethod(enums: Value) {

    import Fruits._

    def getColor: String = enums match {
      case Apple => "RED"
      case Banana => "YELLOW"
      case _ => "NONE"
    }
  }

  val banana = Fruits.Banana

  println( banana.getColor )


  ////////////////////////////////////////////////////////////////////////////////////
  // #8 trait
  ////////////////////////////////////////////////////////////////////////////////////

  class ServiceImportante(name: String) {
    def work(i: Int): Int = {
      println(s"ServiceImportante: Doing important work! ${i}")
      i+1;
    }
  }

  val service1 = new ServiceImportante("uno")

  (1 to 3) foreach (i => println(s"Result: ${service1.work(i)}"))

  trait Logging {
    def info(message: String): Unit
    def warning(message: String): Unit
    def error(message: String): Unit
  }

  trait StdoutLogging extends Logging {
    def info(message: String) = println(s"INFO: ${message}")
    def warning(message: String) = println(s"WARNING: ${message}")
    def error(message: String) = println(s"ERROR: ${message}")
  }

  val service2 = new ServiceImportante("dos") with StdoutLogging {
    override def work(i: Int): Int = {
      info(s"Starting work: i = ${i}")
      val result = super.work(i)
      info(s"Ending work: i = ${i}, result = ${result}")
      result
    }
  }

  (1 to 3) foreach (i => println(s"Result: ${service2.work(i)}"))


}
