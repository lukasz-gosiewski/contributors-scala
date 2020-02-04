package client

sealed trait DomainError
case class BlankNameError() extends DomainError
case class ApiRequestError() extends DomainError