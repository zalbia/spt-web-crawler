# Scala Programming Test - Web Crawler

Runs a ZIO HTTP server on port 8080 that exposes an API endpoint, `POST /api/crawl` that takes a list of URL's, crawls their text content, and returns the
crawled data.

## Format


## Running on Docker

To build a docker image and publish locally in SBT:

    docker:publishLocal

To run built image as a container:

    docker run -d -p 0.0.0.0:8080:8080 --name "spt-web-crawler" spt-web-crawler:0.1.0-SNAPSHOT

Example command to test crawler:

    curl -d '{"urls":["https://google.com","https://github.com"]}' -X POST http://localhost:8080/api/crawl

