# JDBC 2 JSON Schema (J2JS)
A utility application to converts database tables to JSON Schema files.

The current implementation will generate [JSON Schema](https://json-schema.org/) which complies with the [Draft 4 specification](https://json-schema.org/specification-links.html#draft-4)

## Requirements

The following requirement have been defined:

### Input
The application should take a JDBC URL and a table name as the input.

All input arguments should be set via the CLI

### Output
The application should generate a valid JSON schema.

All output should be written to STDOUT

### NULL constraints
If a database column is defined as NOT NULL, it will appear in the JSON schema under the `required` key.

### Default value
If the database column has a default value, it will be used to set the `default` property in the JSON schema.

### Data Type
The database `column type` will be used to define the JSON schema `data type` using the following mapping:

| Database      | JSON Schema |
| ----------- | ----------- |
| integer   | integer        |
| decimal   | number        |
| string   | string        |
| any   | string        |

### Max Length
The database column length will be used, for certain data types, to define constraints in the JSON schema.

| Database      | Constraint |
| ----------- | ----------- |
| string   | maxLength        |
| integer   | maximum        |
| integer   | minimum        |
| decimal   | maximum        |
| decimal   | minimum        |
| datetime   | format        |

### Description
If the database provides column `comment` features, and these values are provided, then populate the `description` property of the JSON schema. 

### Examples
Read *one or more* rows from the database table and set the `examples` property in the JSON schema.  

The `examples` property is defined at the column level and can hold one or more entries. 

## Getting Started

Build the project
```shell
./mvnw package
```

If you prefer to build and run using Maven:
```shell
./mvnw compile exec:java -Dexec.args="jdbc:oracle:thin:@//localhost:1521/ORCLPDB1 lss changeme LSS/ENT_ORDER_CINS"
```

## Installation
The artifacts built by this project are located in the `target/dist` directory.

To install the program, copy this directory and its contents to any machine with Java 11+ installed.

```shell
cd dist/bin
java -cp "./*;../lib/*" consulting.cloudpro.j2js.Application jdbc:oracle:thin:@//localhost:1521/ORCLPDB1 lss changeme LSS/ENT_ORDER_CINS
```
We cannot use `-jar` and `cp` at the same time for `java` command.  Instead, we include the application JAR in the classpath and call the main class manually.

## Database drivers
This application **does not contain any drivers** for connectivity to a database.

You must locate and download a relevant database driver and copy to the `lib` directory in the application installation directory.

The type of driver required by JDBC is based upon the `JDBC URL` that is supplied when running the program.

To ensure database drivers are kept separate from the main application libraries, and each other, it is recommended is to create a new directory, per database version, in the main installation `lib` directory e.g. `<installation-path>/lib/oracle-12g` and copy all the necessary driver JAR files into that directory.  You can then include the driver directory in the `classpath` argument to ensure the drivers are available to the application at runtime.

```shell
java -cp "./*;../lib/*;../lib/oracle-12g/*" ...
```