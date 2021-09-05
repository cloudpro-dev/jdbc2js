package com.example.j2js;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;

public class ApplicationTest {

    private static DatabaseConfig dbConfig;

    private final static String url = "jdbc:h2:mem:testdb";
    private final static String username = "sa";
    private final static String password = "";

    @BeforeClass
    public static void beforeClass() {
        dbConfig = new DatabaseConfig(url, username, password);

        // setup mock data in H2 database
        dbConfig.init();
    }

    @AfterClass
    public static void afterClass() {
        dbConfig.drop();
    }

    @Test
    public void givenValidArguments_whenRunningApplication_thenSucceed() throws SQLException, JsonProcessingException {
        Application app = new Application(url, username, password, "/CUSTOMER");
    }
}
