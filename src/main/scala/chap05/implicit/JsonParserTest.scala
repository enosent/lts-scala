package chap05.`implicit`

//    import scala.util.parsing.json.JSONObject
import org.json4s.jackson.Serialization._

object JsonParserTest {

  implicit val formats = org.json4s.DefaultFormats

  implicit class jsonForStringContext(val sc: StringContext) {
    def jsonParser(values: Any*): String = {
      val keyRE = """^[\s{,]*(\S+):\s*""".r
      val keys = sc.parts map {
        case keyRE(key) => key
        case str => str
      }
      val kvs = keys zip values
      write(kvs.toMap)
    }
  }
}