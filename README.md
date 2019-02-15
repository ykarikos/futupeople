# futupeople

FIXME

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server

## Building

```
lein ring uberjar
docker build -t futupeople .
docker run -p 3000:3000 futupeople
```

## License

Copyright © 2019 FIXME
