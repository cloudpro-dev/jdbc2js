package com.example.j2js.metadata;

import com.example.j2js.DatabaseConfig;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MetadataExtractorTest {

    private static Connection conn;
    private static DatabaseConfig config;

    @BeforeClass
    public static void setup() {
        config = new DatabaseConfig();
        conn = config.getConnection();
        config.init();
    }

    @AfterClass
    public static void afterClass() {
        config.drop();
    }

    @Test
    public void givenSetup_whenSelecting_thenGetRow() throws SQLException {
        MetadataExtractor extractor = new MetadataExtractor(conn);
        Set<ColumnDefinition> results = extractor.extractMetadata("CUSTOMER");
        assertThat(results.size(), is(4));
        System.out.println(results);
    }

}
