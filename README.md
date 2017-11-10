# CMC performance tests

These are the performance tests for CMC powered by [Gatling](http://gatling.io/#/resources/documentation) 
framework. [gradle-gatling-plugin](https://github.com/lkishalmi/gradle-gatling-plugin) is used
for [Gradle](https://gradle.org/) build system integration.

## Getting Started

### Prerequisites

* [Java](https://www.google.co.uk/search?q=how+to+install+openjdk&oq=how+to+install+openjdk) >= v1.8.0,
* [Docker](https://www.docker.com/) to run the tests.

Environment variables to be defined:

```bash
$ export URL="The citizen frontend application URL"
$ export IDAM_API_URL="The IDAM api service URL"
```

### Running the tests:

```bash
$ ./gradlew gatlingRun
```
 
### Debugging:
To read a value from the gatling virtual session use something like:
```
.exec(session => {
        println(">>>>>>>>>>>>>>>>>>>> " + session("state").as[String])
        session
      })
```

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](http://git.reform/cmc/performance-tests/tags).

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE.md) file for details

