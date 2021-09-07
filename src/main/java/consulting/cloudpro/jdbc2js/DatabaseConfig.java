package consulting.cloudpro.jdbc2js;

import java.sql.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseConfig {
    private static final Logger LOG = LoggerFactory.getLogger(DatabaseConfig.class);

    private Connection connection;

    public DatabaseConfig() {
        try {
            // TODO we should setup Database vendor specific JDBC URL
            // h2:mem:testdb;MODE=MSSQLServer
            // h2:mem:testdb;MODE=MySQL
            // h2:mem:testdb;MODE=Oracle
            String url = "jdbc:h2:mem:testdb";
            connection = DriverManager.getConnection(url, "sa", "");
        } catch (SQLException e) {
            LOG.error("Cannot find driver", e);
        }
    }

    public DatabaseConfig(String url, String username, String password) {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            LOG.error("Cannot find driver", e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void init() {
        createTables();
        createData();
    }

    public void drop() {
        dropTables();
    }

    private void createTables() {
        try {
            connection.createStatement().executeUpdate("create table CUSTOMER (ID int primary key auto_increment COMMENT 'Unique customer identifier', NAME VARCHAR(45) NOT NULL DEFAULT 'test' COMMENT 'Name of customer', AGE INTEGER NOT NULL COMMENT 'Age of customer', BALANCE DECIMAL(10,2) NOT NULL COMMENT 'Balance of the customer')");
            connection.createStatement().executeUpdate("create table CUST_ADDRESS (ID int primary key auto_increment, CUST_ID int, ADDRESS VARCHAR(45), FOREIGN KEY (CUST_ID) REFERENCES CUSTOMER(ID))");
        } catch (SQLException e) {
            LOG.error("Cannot create tables", e);
        }
    }

    private void createData() {
        try {
            connection.createStatement().executeUpdate("INSERT INTO CUSTOMER (ID, NAME, AGE, BALANCE) VALUES (1, 'Test Customer', 31, 100.00)");
            connection.createStatement().executeUpdate("INSERT INTO CUST_ADDRESS (ID, CUST_ID, ADDRESS) VALUES (1, 1, 'Test Address')");
        }
        catch (SQLException e) {
            LOG.error("Cannot insert table data", e);
        }
    }

    private void dropTables() {
        try {
            connection.createStatement().executeUpdate("DROP TABLE CUST_ADDRESS");
            connection.createStatement().executeUpdate("DROP TABLE CUSTOMER");
        }
        catch (SQLException e) {
            LOG.error("Cannot drop tables", e);
        }
    }
}
