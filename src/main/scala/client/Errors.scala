package client

import scala.util.control.NoStackTrace

sealed trait DomainError extends Throwable with NoStackTrace {
  def message: String
}

case class BlankNameError(message: String = "Name cannot be blank") extends DomainError
case class ApiRequestError(message: String = "Error occurred while requesting 3rd party API") extends DomainError