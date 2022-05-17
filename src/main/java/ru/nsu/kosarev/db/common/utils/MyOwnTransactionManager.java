package ru.nsu.kosarev.db.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Supplier;

@Component
public class MyOwnTransactionManager {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public <T> T transaction(Supplier<T> func) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);

            try {
                T res = func.get();

                connection.commit();
                return res;
            } catch (Throwable th) {
                connection.rollback();
                throw th;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void transaction(Runnable func) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);

            try {
                func.run();

                connection.commit();
            } catch (Throwable th) {
                connection.rollback();
                throw th;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
