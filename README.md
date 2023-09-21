# XMLBeans Gradle Plugin

##   Overview
This plugin is supposed to simplify execution of https://xmlbeans.apache.org to generate java code for XML usa
It automatically ads plugin java-library to project where is used this plugin.
Currently plugin automatically links project with xmlbeans 3.1.0, but we are working to allow project to decide, which xmlbeans version to use.

## Requirements

Java 8 or higher
Gradle 5.6 or higher
## Installation

To add the plugin to your project, include the following in your build.gradle:

```groovy
plugins {
    id 'lv.brain.gradle.xmlbeans' version '1.0.0'
}
```
### Configuration

To configure the plugin, you can specify the enums you wish to search for using the javaEnumFinder block in your build.gradle file:

```groovy
xsd{
    schema "your_schema_file.xsd"
    config "your_schema_config_file.xsdconfig"
}
```
## Usage

To execute the plugin, run the following Gradle task:

```bash
./gradlew generate-source
```
This will execute  xmlbeans to generate java code for xml parsing/building. generate-source is marked as dependency for javaCompile

```bash
./gradlew build
```
At the end of this task you will have working xml java code compiled and compressed in jar

## Contributing

If you find any issues or have suggestions for improvements, please file an issue or create a pull request.

## License

This project is licensed under the "Mozilla Public License Version 2.0" - see the LICENSE.md file for details.