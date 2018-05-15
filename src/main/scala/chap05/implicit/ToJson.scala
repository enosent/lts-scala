package chap05.`implicit`

object ToJson {
  case class Address(street: String, city: String)
  case class Person(name: String, address: Address)

  trait ToJSON {
    def toJSON(level: Int = 0): String
    val INDENTATION = " "
    def indentation(level: Int = 0): (String, String) =
      (INDENTATION * level, INDENTATION * (level + 1))
  }

  implicit class AddressToJSON(address: Address) extends ToJSON {
    override def toJSON(level: Int): String = {
      val (outdent, indent) = indentation(level)
      s"""{
         |${indent}"street": ${address.street}
         |${indent}"city": ${address.city}
         |${outdent}}""".stripMargin
    }
  }

  implicit class PersonToJSON(person: Person) extends ToJSON {
    override def toJSON(level: Int): String = {
      val (outdent, indent) = indentation(level)
      s"""{
         |${indent}"street": ${person.name}
         |${indent}"city": ${person.address.toJSON(level+1)}
         |${outdent}}""".stripMargin
    }
  }
}
