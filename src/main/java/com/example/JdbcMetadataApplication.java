package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class JdbcMetadataApplication {
    private static final Logger LOG = LoggerFactory.getLogger(JdbcMetadataApplication.class);

    public static void main(String[] args) {
        DatabaseConfig databaseConfig = new DatabaseConfig();
        databaseConfig.init();
        try {
            MetadataExtractor metadataExtractor = new MetadataExtractor(databaseConfig.getConnection());
            metadataExtractor.extractTableInfo();
            metadataExtractor.extractSystemTables();
            metadataExtractor.extractViews();
            String tableName = "CUSTOMER";
            metadataExtractor.extractColumnInfo(tableName);
            metadataExtractor.extractPrimaryKeys(tableName);
            metadataExtractor.extractForeignKeys("CUST_ADDRESS");
            metadataExtractor.extractDatabaseInfo();
            metadataExtractor.extractUserName();
            metadataExtractor.extractSupportedFeatures();
        } catch (SQLException e) {
            LOG.error("Error while executing SQL statements", e);
        }
    }
}
