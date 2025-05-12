# Nutricion - Maven Project

This is a Maven conversion of the Nutricion project, a Java application that uses SWI-Prolog through the JPL library to generate diet menus based on caloric requirements.

## Project Structure

- `src/nutricion/` - Java source files
- `img/` - Images used in the application
- `knowledge.pl` - Prolog knowledge base with diet rules
- `lib/` - Directory for external libraries (you need to add jpl.jar here)

## Setup Instructions

### Prerequisites

- Java 22 or compatible version
- Maven 3.6 or newer
- SWI-Prolog installed (for the JPL library)

### Setting up the JPL Dependency

Before building the project, you need to set up the JPL dependency:

1. Obtain the jpl.jar file from your SWI-Prolog installation or from the original project
2. Place it in the `lib` directory of this project
3. Install it to your local Maven repository with the following command:

```bash
mvn install:install-file -Dfile=lib/jpl.jar -DgroupId=org.jpl7 -DartifactId=jpl -Dversion=7.8.0 -Dpackaging=jar
```

### Building the Project

After setting up the JPL dependency, you can build the project with:

```bash
mvn clean package
```

This will create a JAR file in the `target` directory.

### Running the Application

You can run the application with:

```bash
java -jar target/Nutricion-1.0-SNAPSHOT.jar
```

Make sure that SWI-Prolog is properly installed and configured so that the JPL library can connect to it.

## Notes on Maven Conversion

This project was converted from an Ant-based NetBeans project to Maven. The conversion includes:

- Creating a pom.xml file with appropriate dependencies and build configuration
- Setting up the project structure to work with Maven
- Configuring resources to include the Prolog knowledge base and images

The original functionality of the application remains unchanged.