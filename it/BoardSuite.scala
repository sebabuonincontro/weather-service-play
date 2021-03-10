import containers.ContainersPerSuite
import org.scalatest.TestData
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneServerPerTest
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder

class BoardSuite extends PlaySpec
  with GuiceOneServerPerTest
  with ContainersPerSuite {

  override def newAppForTest(testData: TestData): Application =
    new GuiceApplicationBuilder()
      .configure(
        Map[String, Any](
          "play.evolutions.db.default.autoApply" -> true,
          "play.evolutions.db.default.enabled"   -> true,
          "slick.dbs.default.profile"            -> "slick.jdbc.MySQLProfile$",
          "slick.dbs.default.db.url"             -> mysqlContainer.jdbcUrl,
          "slick.dbs.default.db.user"            -> mysqlContainer.username,
          "slick.dbs.default.db.password"        -> mysqlContainer.password
        )
      )
      .build()

  "create Board suite" in {
    behave
  }

}
