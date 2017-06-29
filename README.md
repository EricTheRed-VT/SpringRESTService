# SpringRESTService
A bookmark storage service with a HATEOAS-compliant REST API.

Made with Spring Boot, HATEOAS, and Data JPA; utilizing an H2 database for storage, JUnit for testing, and Maven for build/run scripts.

### To Run:
In a terminal, call ```mvn clean spring-boot:run``` to build and run the server.

### Routes:
GET ```http://localhost:8080/{username}/bookmarks```: returns a JSON of all bookmarks associated with ```{username}```.

GET ```http://localhost:8080/{username}/bookmarks/{id}```: returns a JSON of the bookmark with an id of ```{id}``` associated with ```{username}```.

POST ```http://localhost:8080/{username}/bookmarks```: adds a new bookmark from a JSON, associated with ```{username}```, with the next available id.

Routes will return a ```404: UserNotFoundException "could not find user '{username}'."``` if ```{username}``` does not match an existing user.

### Bookmarks/Data:
Bookmarks are represented as JSON objects with the structure:

```
{
	id: 3,
	uri: "http://bookmark.com/1/bob",
	description: "A description"
}
```

These objects are returned with links in HAL format. For example ```http://localhost:8080/bob/bookmarks/3``` returns:

```
{
    "bookmark": {
        "id": 3,
        "uri": "http://bookmark.com/1/bob",
        "description": "A description"
    },
    "_links": {
        "bookmark-uri": {
            "href": "http://bookmark.com/1/bob"
        },
        "bookmarks": {
            "href": "http://localhost:8080/bob/bookmarks"
        },
        "self": {
            "href": "http://localhost:8080/bob/bookmarks/3"
        }
    }
}
```

and ```http://localhost:8080/bob/bookmarks/``` returns a list of bookmarks with appropriate links:

```
    "_embedded": {
        "bookmarkResourceList": [
            {
                "bookmark": {
                    "id": 3,
                    "uri": "http://bookmark.com/1/bob",
                    "description": "A description"
                },
                "_links": {
                    "bookmark-uri": {
                        "href": "http://bookmark.com/1/bob"
                    },
                    "bookmarks": {
                        "href": "http://localhost:8080/bob/bookmarks"
                    },
                    "self": {
                        "href": "http://localhost:8080/bob/bookmarks/3"
                    }
                }
            },
            {
                "bookmark": {
                    "id": 4,
                    "uri": "http://bookmark.com/2/bob",
                    "description": "A description"
                },
                "_links": {
                    "bookmark-uri": {
                        "href": "http://bookmark.com/2/bob"
                    },
                    "bookmarks": {
                        "href": "http://localhost:8080/bob/bookmarks"
                    },
                    "self": {
                        "href": "http://localhost:8080/bob/bookmarks/4"
                    }
                }
            }
        ]
    }
}
```