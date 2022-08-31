# Scala Programming Test - Web Crawler

Runs a ZIO HTTP server on port 8080 that exposes an API endpoint, `POST /api/crawl` that takes a JSON object containing
a list of URL's, crawls their text content, and returns the
crawled data or a message detailing a crawling error.

## Running

Run the server with `sbt run`.

Run the test suite with `sbt test`.

## Running on Docker

To build a docker image and publish locally in SBT:

    sbt> docker:publishLocal

To run built image as a container:

    sbt> docker run -d -p 0.0.0.0:8080:8080 --name "spt-web-crawler" spt-web-crawler:0.1.0-SNAPSHOT

Example command to test crawler:

    curl -d '{"urls":["https://google.com","https://github.com"]}' -X POST http://localhost:8080/api/crawl

