crawlspec
========

A declarative configuration format for defining web archive crawl jobs. Plus a reference implementation consisting 
of a command-line tool and java library for generating crawler-specific configuration and running crawls.

**Status:** just getting started and many things will change. The plan is to create a schema file and
documentation once things are more fleshed out.  At the moment the schema is defined by
[Job.java](src/org/netpreserve/crawlspec/job/Job.java).

Crawlers currently supported by the reference implementation: Browsertrix, Heritrix, HTTrack, Wget.
Each crawler only supports a subset of the defined options. See the `@SupportedBy` annotation on
each option.

Example job config:

```json
{
  "id": "example-job",
  "schedule": "0 */2 * * 1-5",
  "userAgent": "crawlspec_bot",
  "timeLimit": 3600,
  "sizeLimit": 100000000,
  "robotsPolicy": "obey",
  "seeds": [
    {
      "url": "https://example.org/",
      "scope": "domain",
      "robotsPolicy": "ignore"
    },
    {
      "url": "https://www.example.com/foo/bar.html"
    },
    {
      "url": "https://department.example/publications/annual-report-2020",
      "scope": "page"
    }
  ],
  "cookies": [
    {
      "domain": "example.org",
      "path": "/",
      "name": "sessionid",
      "value": "9945453f7ca22",
      "secure": true,
      "httpOnly": false,
      "expirationDate": 1623766184
    }
  ]
}
```

Expected usage: (not functional yet)

```shell
# Build a Heritrix job (crawler-beans.cxml, seeds.txt etc) 
crawlspec build myjob.json --crawler heritrix --output /heritrix/jobs/myjob/

# Immediately run a crawl job
crawlspec run --crawler wget myjob.json

# Check job config is valid and options are supported by the given crawler
crawlspec validate --crawler wget myjob.json
```