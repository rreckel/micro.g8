
Summary
-------
A [Giter8][g8] template for VDL micro services

```
sbt new rreckel/micro.g8 --branch base
sbt run
```

Branches
--------

* base - Base project. Just prints "Hello World"
* server - This branch uses an Akka Http server with endpoints
* tagless_server - This branch is based on the 'server' branch, but uses final tagless endcoding. It also includes logging and error handling 

Template license
----------------
Written in 2019 by Roland Reckel (rreckel@vdl.lu)

To the extent possible under law, the author(s) have dedicated all copyright and related
and neighboring rights to this template to the public domain worldwide.
This template is distributed without any warranty. See <http://creativecommons.org/publicdomain/zero/1.0/>.

[g8]: http://www.foundweekends.org/giter8/
