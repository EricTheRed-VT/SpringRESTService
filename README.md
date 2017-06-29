# SpringRESTService
A basic bookmark storage service with a REST API. Made with Spring Boot, Spring Data JPA, an H2 database, JUnit, and Maven.

### To Run:
In a terminal, call ```mvn clean spring-boot:run``` to build and run the server.

### Bookmarks:
Bookmarks are represented as JSON objects with the structure:

```
{
	id: 6,
	uri: "http://bookmark.com/2/bob",
	description: "A description"
}
```

### Routes:
GET ```http://localhost:8080/{username}/bookmarks```: returns a JSON of all bookmarks associated with ```{username}```.
  
GET ```http://localhost:8080/{username}/bookmarks/{id}```: returns a JSON of the bookmark with an id of ```{id}``` associated with ```{username}```.
  
POST ```http://localhost:8080/{username}/bookmarks```: adds a new bookmark from a JSON, associated with ```{username}```, with the next available id for that user.
  
Routes will return a ```404: UserNotFoundException "could not find user '{username}'."``` if ```{username}``` does not match an existing user.
