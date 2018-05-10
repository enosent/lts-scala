package chap05

import scala.util.{Failure, Success}

object Futures extends App {

  import scala.concurrent.Future
  import scala.concurrent.ExecutionContext.Implicits.global

  def sleep(mills: Long) = {
    Thread.sleep(mills)
  }

  def doWork(index: Int) = {
    sleep((math.random * 1000).toLong)
  }

  (1 to 5) foreach { index =>
    val future = Future {
      doWork(index)
    }
    future onComplete {
      case Success(_) => println(s"Success! return: ${index}")
      case Failure(e) => println(s"FAILURE! returned: ${e}")
    }
  }

  sleep(1000)

  println("Finito!")

}
