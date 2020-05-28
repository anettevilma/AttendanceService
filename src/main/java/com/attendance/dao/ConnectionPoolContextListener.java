package com.attendance.dao;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
//import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@SuppressWarnings("unchecked")
@WebListener
public class ConnectionPoolContextListener implements ServletContextListener {

    // Saving credentials in environment variables is convenient, but not secure - consider a more
    // secure solution such as https://cloud.google.com/kms/ to help keep secrets safe.
    private static final String CLOUD_SQL_CONNECTION_NAME ="attendance-274813:us-central1:attendance";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "root";
    private static final String DB_NAME = "attendance";

    @SuppressWarnings("unchecked")
    private DataSource createConnectionPool() {
        // [START cloud_sql_mysql_servlet_create]
        // The configuration object specifies behaviors for the connection pool.
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(String.format("jdbc:mysql:///%s", DB_NAME));
        config.setUsername(DB_USER); // e.g. "root", "postgres"
        config.setPassword(DB_PASS); // e.g. "my-password"
        config.addDataSourceProperty("socketFactory", "com.google.cloud.sql.mysql.SocketFactory");
        config.addDataSourceProperty("cloudSqlInstance", CLOUD_SQL_CONNECTION_NAME);
        config.setMaximumPoolSize(5);
        config.setMinimumIdle(5);
        config.setConnectionTimeout(10000); // 10 seconds
        config.setIdleTimeout(600000); // 10 minutes
        config.setMaxLifetime(1800000); // 30 minutes
        DataSource pool = new HikariDataSource(config);
        // [END cloud_sql_mysql_servlet_create]
        return pool;
    }

    private void createTable(DataSource pool) throws SQLException {
        // Safely attempt to create the table schema.
        try (Connection conn = pool.getConnection()) {
            String stmt =
                    "CREATE TABLE IF NOT EXISTS votes ( "
                            + "vote_id SERIAL NOT NULL, time_cast timestamp NOT NULL, candidate CHAR(6) NOT NULL,"
                            + " PRIMARY KEY (vote_id) );";
            try (PreparedStatement createTableStatement = conn.prepareStatement(stmt); ) {
                createTableStatement.execute();
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        // This function is called when the Servlet is destroyed.
        HikariDataSource pool = (HikariDataSource) event.getServletContext().getAttribute("my-pool");
        if (pool != null) {
            pool.close();
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        // This function is called when the application starts and will safely create a connection pool
        // that can be used to connect to.
        System.out.println("Inside context initialization fnc");
        ServletContext servletContext = event.getServletContext();
        DataSource pool = (DataSource) servletContext.getAttribute("my-pool");
        if (pool == null) {
            pool = createConnectionPool();
            servletContext.setAttribute("my-pool", pool);
        }
        try {
            createTable(pool);
        } catch (SQLException ex) {
            throw new RuntimeException(
                    "Unable to verify table schema. Please double check the steps"
                            + "in the README and try again.",
                    ex);
        }
    }
}
