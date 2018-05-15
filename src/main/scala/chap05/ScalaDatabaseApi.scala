package chap05

object ScalaDatabaseApi extends App {

  import chap05.`implicit`.implicits._

  val row = javadb.JRow("one" -> 1, "two" -> 2.2, "three" -> "THREE!")

  val oneValue1: Int      = row.get("one")
  val twoValue1: Double   = row.get("two")
  val threeValue1: String = row.get("three")
  // val fourValue1: Byte    = row.get("four")  // won't compile

  println(s"one1   -> $oneValue1")
  println(s"two1   -> $twoValue1")
  println(s"three1 -> $threeValue1")

  val oneValue2   = row.get[Int]("one")
  val twoValue2   = row.get[Double]("two")
  val threeValue2 = row.get[String]("three")
  // val fourValue2    = row.get[Byte]("four")  // won't compile

  println(s"one2   -> $oneValue2")
  println(s"two2   -> $twoValue2")
  println(s"three2 -> $threeValue2")

}
