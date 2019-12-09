
Summary
-------

Micro service using tagless final pattern with keymaker authorization

Only 4 endpoints exist:
* /healthz - health check
* /api/v1/hello - return "Hello World" - this endpoint is secured, so you need to send an Authorization header with a valid keyxmaker token
* /api/v1/explode - returns a DomainError
* /api/v1/exception - returns a SystemError (an uncatched Exception is thrown)

Start the server on http://localhost:8080 with
```
sbt run
```
