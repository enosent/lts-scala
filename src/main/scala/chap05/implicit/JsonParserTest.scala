package chap05.`implicit`

//    import scala.util.parsing.json.JSONObject
import org.json4s.jackson.Serialization._

object JsonParserTest {




  implicit class jsonForStringContext(val sc: StringContext) {
    def jsonParser(values: Any*): String = {
      val keyRE = """^[\s{,]*(\S+):\s*""".r
      val keys = sc.parts map {
        case keyRE(key) => key
        case str => str
      }
      val kvs = keys zip values

      implicit val ff = org.json4s.DefaultFormats
      write(kvs.toMap)
    }
  }


}