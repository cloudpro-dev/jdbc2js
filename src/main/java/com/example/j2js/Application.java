package com.example.j2js;

import com.example.j2js.generator.JsonSchemaGenerator;
import com.example.j2js.metadata.MetadataExtractor;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.sql.SQLException;

public class Application {

    /**
     * Creates a new instance of the SQL2JsonSchema application
     * @param url the url of the database
     * @param username the username for database access
     * @param password the password for database access
     * @param tableName the name of the table to extract
     * @throws SQLException any database errors
     * @throws JsonProcessingException any JSON conversion errors
     */
    public Application(String url, String username, String password, String tableName) throws SQLException, JsonProcessingException {
        DatabaseConfig databaseConfig = new DatabaseConfig(url, username, password);
        MetadataExtractor extractor = new MetadataExtractor(databaseConfig.getConnection());
        JsonSchemaGenerator schemaGenerator = new JsonSchemaGenerator();
        String jsonSchema = schemaGenerator.generateSchema(extractor.extractMetadata(tableName));
        System.out.println(jsonSchema);
    }

    /**
     * Main method to run the application
     * @param args - command line arguments
     */
    public static void main(String[] args) throws Exception {
        if(args.length != 4) {
            printUsage();
            System.exit(1);
        }
        String jdbcUrl = args[0];
        String username = args[1];
        String password = args[2];
        String tableName = args[3];

        Application app = new Application(jdbcUrl, username, password, tableName);
    }

    private static void printUsage() {
        System.out.println("Usage: java -jar sql2schema.jar url username password table");
    }

}
