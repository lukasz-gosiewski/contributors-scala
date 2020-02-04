package client

case class RepositoryResponseDto(name: String)
case class ContributorResponseDto(login: String, contributions: Int)