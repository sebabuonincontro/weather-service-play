package repositories.tables

import slick.lifted.TableQuery

object Tables {
  val boardsTable = TableQuery[BoardTable]
  val boardLocationTable = TableQuery[BoardLocationTable]
  val locationTable = TableQuery[LocationTable]
}
