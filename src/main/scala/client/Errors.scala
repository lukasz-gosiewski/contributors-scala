package client

sealed trait DomainError {
  def message: String
}

case class BlankNameError(message: String = "Name cannot be blank") extends DomainError()
case class ApiRequestError(message: String = "Error occurred while requesting 3rd party API") extends DomainError