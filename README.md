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
$ export IDAM_URL="The IDAM api service URL"
```

### Running the tests:

Against a local environment running on your machine:

```bash
$ ./gradlew gatlingRun
```
 
```bash
$ ./bin/run-performance-tests.sh
```

## Versioning

We use [SemVer](http://semver.org/) for versioning. For the versions available, see the [tags on this repository](http://git.reform/cmc/performance-tests/tags).

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE.txt) file for details

