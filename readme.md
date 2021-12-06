### Background and goal
GitHub portal is centered around organizations and repositories. Each organization has many repositories and each 
repository has many contributors. The goal is to create an endpoint that given the name of the organization will
return a list of contributors sorted by the number of contributions (this is for a start).

### The endpoint should:
* use GitHub REST API v3 (https://developer.github.com/v3/)
* return a list sorted by the number of contributions made by developer to all repositories for the given organization.
* respond to a GET request at port `8080` and address `/org/{org_name}/contributors`
* respond with JSON, which should be a list of contributors in the following format: `{ “name”: <contributor_login>, “contributions”: <no_of_contributions> }`
* handle GitHub’s API rate limit restriction using token that can be set as an environment variable of name `GH_TOKEN`
* friendly hint: GitHub API returns paginated responses. You should take it into account if you want the result to be accurate for larger organizations.

### Additional requirements
* code quality (idiomatic Scala, functional programming preferred)
* design quality (proper abstractions)
* tests
* taking advantage of the framework in use
* HTTP protocol usage (response codes, headers, caching, etc.)
* performance