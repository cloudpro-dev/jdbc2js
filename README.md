# JDBC 2 JSON Schema (J2JS)
A utility application to convert JDBC RDBMS tables to JSON Schema files.

This project is part of the FieldViewJS project which visualises mappings between data in different domains.  This program is used to ingest data from a database and generate a JSON schema file which can then be used as an input into the FieldViewJS application. 

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
If a database column is defined as NULL, it will appear in the JSON schema under the `required` key.

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

