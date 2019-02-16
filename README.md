# futupeople

FIXME

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running locally

To start a web server for the application, run:

    lein ring server-headless

## Building and running in Docker

```
lein ring uberjar
docker build -t futupeople .
docker run -p 8000:8000 futupeople
```

## Deploy to FutuSwarm

```
lein ring uberjar
docker build -t futurice/futupeople:v1 .
playswarm image:push -i futurice/futupeople -t v1
playswarm app:deploy -i futurice/futupeople -t v1 -n futupeople
```

## License

Copyright © 2019 FIXME
