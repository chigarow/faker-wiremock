---
# YAML header
render_macros: false
---

# Faker Extension for WireMock

Uses [Data Faker](https://github.com/datafaker-net/datafaker) to generate random, fake data for using in WireMock responses. 

## Usage

### Step 1: Add to your build file

For Maven users:

```xml
<dependency>
    <groupId>org.wiremock.extensions</groupId>
    <artifactId>wiremock-faker-extension</artifactId>
    <version>0.2.0</version>
</dependency>
```

For Gradle users:

```groovy
dependencies {
    implementation 'org.wiremock.extensions:wiremock-faker-extension-standalone:0.2.0'
}
```

### Step 2: Register the extension with your server

Then, register this extension as you would any other:

```java
new WireMockServer(wireMockConfig().extensions(RandomExtension.class));
```

### Step 3: Use it in your stubs!

{% raw %}
```handlebars
{{ random 'id-ID.Name.first_name' }}
```
{% endraw %}

This will generate random first names in the `id-ID` locale for every request.
Can check on this site for others locale: https://fakerjs.dev/guide/localization


### Technical notes
This library brings `net.datafaker:datafaker` as transitive dependency, which may result in conflicts at building time. 
If that's the case, the `net.datafaker:datafaker` dependency needs to be excluded. 

For a full reference of the available keys, see the [Reference Documentation](./docs/reference.md).