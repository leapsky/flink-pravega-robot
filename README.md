# Pravega Robot Connectors Example

This repository contains code samples to demonstrate how developers can work with 
[Pravega](http://pravega.io).

For more information on Pravega, we recommend to read the [documentation and the
developer guide](http://pravega.io).

> Hint: Have a look to the [terminology and concepts](http://pravega.io/docs/latest/terminology/) in Pravega.

| Example Name  | Description  | Language |
| ------------- |:-----| :-----|
| `cozmo` | This sample demonstrates how to create a robot with object recognition using Pravega + Flink. It allaws to show a "Cozmo" robot's current location in the maze and find toys using visual recognition functionality. | [Java](flink-connector-examples/src/main/java/io/pravega/sharktank/flink/cozmo)

The related documentation and instructions are [here](flink-connector-examples).


# Build Instructions

Next, we provide instructions for building the `pravega-samples` repository. There are two main options: 
- _Out-of-the-box_: If you want a quick start, run the samples by building `pravega-samples` out-of-the-box
(go straight to section `Pravega Samples Build Instructions`). 
- _Build from source_: If you want to have fun building the different projects from source, please read
section `Building Pravega Components from Source (Optional)` before building `pravega-samples`. 

## Pre-requisites

* Java 8

## Building Pravega Components from Source (Optional)

### Pravega Build Instructions 

If you want to build Pravega from source, you may need to generate the latest Pravega `jar` files and install them to 
your local Maven repository. To build Pravega from sources and use it here, please run the following commands:

```
$ git clone https://github.com/pravega/pravega.git
$ cd pravega
$ ./gradlew install
```

The above command should generate the required `jar` files into your local Maven repository.

> Hint: For using in the sample applications the Pravega version you just built, you need to update the 
`pravegaVersion=<local_maven_pravega_version>` property in `gradle.properties` file 
of `pravega-samples`.

For more information, please visit [Pravega](https://github.com/pravega/pravega).

## Example Build Instructions

The `flink-pravega-robot` project is prepared for working out-of-the-box with 
[release artifacts](https://github.com/pravega/pravega/releases) of Pravega components, which are already 
available in Maven central. To build `pravega-samples` from source, use the built-in gradle wrapper as follows:

```
$ git clone https://github.com/leapsky/flink-pravega-robot.git
$ cd pravega-samples
$ ./gradlew clean installDist
```
That's it! You are good to go and execute the examples :) 

# Where to Find Help

Documentation on Pravega and Analytics Connectors:
* [Pravega.io](http://pravega.io/), [Pravega Wiki](https://github.com/pravega/pravega/wiki).
* [Flink Connectors Wiki](https://github.com/pravega/flink-connectors/wiki).

Did you find a problem or bug?
* First, check our [FAQ](http://pravega.io/docs/latest/faq/).
* If the FAQ does not help you, create a [new GitHub issue](https://github.com/pravega/pravega-samples/issues).

Do you want to contribute a new example application?
* Follow the [guidelines for contributors](https://github.com/pravega/pravega/wiki/Contributing).

Have fun!!
