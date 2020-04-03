package repositories.tables

import slick.lifted.TableQuery

object Tables {
  val boardsTable = TableQuery[BoardTable]
  val boardsLocationTable = TableQuery[BoardLocationTable]
  val locationTable = TableQuery[LocationTable]
}
