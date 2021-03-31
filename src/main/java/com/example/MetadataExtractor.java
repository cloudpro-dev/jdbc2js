package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MetadataExtractor {
    private static final Logger LOG = LoggerFactory.getLogger(MetadataExtractor.class);

    private final DatabaseMetaData databaseMetaData;

    public MetadataExtractor(Connection connection) throws SQLException {
        this.databaseMetaData = connection.getMetaData();
        DatabaseMetaData databaseMetaData = connection.getMetaData();
    }

    public void extractTableInfo() throws SQLException {
        ResultSet resultSet = databaseMetaData.getTables(null, null, "CUST%", new String[] { "TABLE" });
        while (resultSet.next()) {
            // Print the names of existing tables
            LOG.info(resultSet.getString("TABLE_NAME"));
            LOG.info(resultSet.getString("REMARKS"));
        }
    }

    public void extractSystemTables() throws SQLException {
        ResultSet resultSet = databaseMetaData.getTables(null, null, null, new String[] { "SYSTEM TABLE" });
        while (resultSet.next()) {
            // Print the names of system tables
            LOG.info(resultSet.getString("TABLE_NAME"));
        }
    }

    public void extractViews() throws SQLException {
        ResultSet resultSet = databaseMetaData.getTables(null, null, null, new String[] { "VIEW" });
        while (resultSet.next()) {
            // Print the names of existing views
            LOG.info(resultSet.getString("TABLE_NAME"));
        }
    }

    public void extractColumnInfo(String tableName) throws SQLException {
        ResultSet columns = databaseMetaData.getColumns(null, null, tableName, null);

        while (columns.next()) {
            String columnName = columns.getString("COLUMN_NAME");
            String columnSize = columns.getString("COLUMN_SIZE");
            String datatype = columns.getString("DATA_TYPE");
            String isNullable = columns.getString("IS_NULLABLE");
            String isAutoIncrement = columns.getString("IS_AUTOINCREMENT");
            LOG.info(String.format("ColumnName: %s, columnSize: %s, datatype: %s, isColumnNullable: %s, isAutoIncrementEnabled: %s", columnName, columnSize, datatype, isNullable, isAutoIncrement));
        }
    }

    public void extractPrimaryKeys(String tableName) throws SQLException {
        ResultSet primaryKeys = databaseMetaData.getPrimaryKeys(null, null, tableName);
        while (primaryKeys.next()) {
            String primaryKeyColumnName = primaryKeys.getString("COLUMN_NAME");
            String primaryKeyName = primaryKeys.getString("PK_NAME");
            LOG.info(String.format("columnName:%s, pkName:%s", primaryKeyColumnName, primaryKeyName));
        }
    }

    public void fun() throws SQLException {

    }

    public void extractForeignKeys(String tableName) throws SQLException {
        ResultSet foreignKeys = databaseMetaData.getImportedKeys(null, null, tableName);
        while (foreignKeys.next()) {
            String pkTableName = foreignKeys.getString("PKTABLE_NAME");
            String fkTableName = foreignKeys.getString("FKTABLE_NAME");
            String pkColumnName = foreignKeys.getString("PKCOLUMN_NAME");
            String fkColumnName = foreignKeys.getString("FKCOLUMN_NAME");
            LOG.info(String.format("pkTableName:%s, fkTableName:%s, pkColumnName:%s, fkColumnName:%s", pkTableName, fkTableName, pkColumnName, fkColumnName));
        }
    }

    public void extractDatabaseInfo() throws SQLException {
        String productName = databaseMetaData.getDatabaseProductName();
        String productVersion = databaseMetaData.getDatabaseProductVersion();

        String driverName = databaseMetaData.getDriverName();
        String driverVersion = databaseMetaData.getDriverVersion();

        LOG.info(String.format("Product name:%s, Product version:%s", productName, productVersion));
        LOG.info(String.format("Driver name:%s, Driver Version:%s", driverName, driverVersion));
    }

    public void extractUserName() throws SQLException {
        String userName = databaseMetaData.getUserName();
        LOG.info(userName);
        ResultSet schemas = databaseMetaData.getSchemas();
        while (schemas.next()) {
            String table_schem = schemas.getString("TABLE_SCHEM");
            String table_catalog = schemas.getString("TABLE_CATALOG");
            LOG.info(String.format("Table_schema:%s, Table_catalog:%s", table_schem, table_catalog));
        }
    }

    public void extractSupportedFeatures() throws SQLException {
        LOG.info("Supports scrollable & Updatable Result Set: " + databaseMetaData.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE));
        LOG.info("Supports Full Outer Joins: " + databaseMetaData.supportsFullOuterJoins());
        LOG.info("Supports Stored Procedures: " + databaseMetaData.supportsStoredProcedures());
        LOG.info("Supports Subqueries in 'EXISTS': " + databaseMetaData.supportsSubqueriesInExists());
        LOG.info("Supports Transactions: " + databaseMetaData.supportsTransactions());
        LOG.info("Supports Core SQL Grammar: " + databaseMetaData.supportsCoreSQLGrammar());
        LOG.info("Supports Batch Updates: " + databaseMetaData.supportsBatchUpdates());
        LOG.info("Supports Column Aliasing: " + databaseMetaData.supportsColumnAliasing());
        LOG.info("Supports Savepoints: " + databaseMetaData.supportsSavepoints());
        LOG.info("Supports Union All: " + databaseMetaData.supportsUnionAll());
        LOG.info("Supports Union: " + databaseMetaData.supportsUnion());
    }
}
