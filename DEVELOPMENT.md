# Development Guide

## Publish Snapshot Version for local Development

To publish snapshot version to Maven Local change `build.gradle`:

```
plugins {
    id 'maven-publish'
}
```

And run:

```
./gradlew publishToMavenLocal
```

## Increase Version

By default, only patch version is increased. To increase minor or major version, run:

```
./gradlew markNextVersion -Prelease.version=1.0.0
```

See [Axion plugin](https://github.com/allegro/axion-release-plugin) for more details.
