package client

sealed trait DomainError extends Throwable

case class BlankNameError() extends DomainError {

  override def getMessage: String = "Name cannot be blank"
}

case class ApiRequestError(statusCode: Int) extends DomainError {
  override def getMessage: String = s"Error occurred while requesting 3rd party API, status code: $statusCode"
}

case class OrganizationDoesNotExistsError(organizationName: String) extends DomainError {
  override def getMessage: String = s"Organization $organizationName does not exists"
}