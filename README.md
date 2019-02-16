# futupeople

Calculate live summary from all [Futurice](https://futurice.com/) people. It is running at https://futupeople.play.futurice.com/

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Environment variables

- `PEOPLE_LIST` list of all people in JSON format, e.g. `https://reports.app.futurice.com/futuqu/rada/people`

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
export TAG=$(git rev-parse --short HEAD)
docker build -t futurice/futupeople:$TAG .
playswarm image:push -i futurice/futupeople -t $TAG
playswarm app:deploy -i futurice/futupeople -t $TAG -n futupeople
```

More info on FutuSwarm here: https://futuswarm-mainpage.play.futurice.com/

## License

Licensed with [MIT License](LICENSE).
