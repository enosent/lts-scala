package chap05

object implicits {
  import javadb.JRow

  implicit class SRow(jrow: JRow) {
    def get[T](colName: String)(implicit toT: (JRow,String) => T): T =
      toT(jrow, colName)
  }

  implicit val jrowToInt: (JRow,String) => Int = (jrow: JRow, colName: String) => jrow.getInt(colName)
  implicit val jrowToDouble: (JRow,String) => Double = (jrow: JRow, colName: String) => jrow.getDouble(colName)
  implicit val jrowToString: (JRow,String) => String = (jrow: JRow, colName: String) => jrow.getText(colName)
}