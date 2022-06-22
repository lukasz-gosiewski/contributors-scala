package controller

import org.http4s._
import cats.effect._
import org.http4s.dsl.io._


object HelloWorldController {

  val helloWorldRoutes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "hello" / name => Ok(this.getGreeting(name))
  }

  def getGreeting(name: String) = s"Hello, $name."
}
