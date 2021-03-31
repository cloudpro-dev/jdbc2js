import com.example.DatabaseConfig;
import com.example.MetadataExtractor;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcMetadataApplicationTest {

    private static Connection conn;
    private static DatabaseConfig config;

    @BeforeClass
    public static void setup() throws Exception {
        config = new DatabaseConfig();
        conn = config.getConnection();
        config.init();
    }

    @Test
    public void givenSetup_whenSelecting_thenGetRow() throws SQLException {
        MetadataExtractor metadataExtractor = new MetadataExtractor(config.getConnection());
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

    }
}
