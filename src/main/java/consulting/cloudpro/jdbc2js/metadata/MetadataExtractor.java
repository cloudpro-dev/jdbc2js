package consulting.cloudpro.jdbc2js.metadata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

/**
 * Extracts column meta-data using the specified {@link Connection}.
 */
public class MetadataExtractor {
    private static final Logger LOG = LoggerFactory.getLogger(MetadataExtractor.class);

    private final DatabaseMetaData databaseMetaData;
    private final Connection connection;

    /**
     * Create a new instance
     * @param conn database connection
     */
    public MetadataExtractor(Connection conn)  throws SQLException {
        this.connection = conn;
        this.databaseMetaData = conn.getMetaData();
    }

    /**
     * Extract meta-data from the specified table
     * @param tableName the table name
     * @return a list of column definitions
     */
    public Set<ColumnDefinition> extractMetadata(String schema, String tableName) throws SQLException {
        ResultSet columns = databaseMetaData.getColumns(null, schema, tableName, null);

        Collection<String> primaryKeyColumnNames = new ArrayList<>();
        ResultSet primaryKeys = databaseMetaData.getPrimaryKeys(null, null, tableName);
        while (primaryKeys.next()) {
            primaryKeyColumnNames.add(primaryKeys.getString("COLUMN_NAME"));
        }

        // execute select SQL statement to get a row
        String selectTableSQL = "SELECT * from " + tableName;
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(selectTableSQL);
        boolean exampleExists = rs.next();

        Set<ColumnDefinition> definitions = new HashSet<>();
        while (columns.next()) {
            String columnName = columns.getString("COLUMN_NAME");

            ColumnDefinition def = new ColumnDefinition();
            def.setSchema(columns.getString("TABLE_SCHEM"));
            def.setCatalog(columns.getString("TABLE_CAT"));
            def.setTable(columns.getString("TABLE_NAME"));
            def.setName(columnName);
            def.setType(JDBCType.valueOf(columns.getInt("DATA_TYPE")).getName());
            def.setSize(columns.getInt("COLUMN_SIZE"));
            // any column which is a PK or has a NOT NULL constraint
            def.setNullable(columns.getString("IS_NULLABLE"));
            // primary keys cant have a default value and some time return a
            // complex sequence SELECT statement
            if(!primaryKeyColumnNames.contains(columnName)) {
                def.setDefaultValue(columns.getString("COLUMN_DEF"));
                // get example value from loaded row
                if(exampleExists) {
                    // do not let example extraction fail the task
                    try {
                        // read column value from selected row as an example value
                        def.setExampleValue(rs.getString(columnName));
                    }
                    catch(Exception ex) {
                        LOG.error(String.format("Cannot set example value for column %s", columnName), ex);
                    }
                }
            }
            def.setDecimals(columns.getInt("DECIMAL_DIGITS"));
            def.setRemarks(columns.getString("REMARKS"));
            definitions.add(def);
        }

        return definitions;
    }

}
