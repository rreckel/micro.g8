
Summary
-------

Micro service using tagless final pattern.

Only 2 endpoints exist:
* /healthz - health check
* /api/v1/hello - return "Hello World"
* /api/v1/explode - returns an error

Start the server on http://localhost:8080 with
```
sbt run
```
