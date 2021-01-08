package containers

import com.dimafeng.testcontainers.{Container, ForAllTestContainer, KafkaContainer, MultipleContainers, MySQLContainer}
import org.scalatestplus.play.PlaySpec

trait ContainersPerSuite extends ForAllTestContainer { _ : PlaySpec =>

  protected val mysqlContainer = MySQLContainer(
    mysqlImageVersion = "mysql:8.0.17",
    databaseName = "weather",
    username = "root",
    password = "root"
  )

  protected val yahooContainer = null

  override val container: Container = MultipleContainers (
    mysqlContainer,
    yahooContainer
  )
}
