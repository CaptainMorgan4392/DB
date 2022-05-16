package ru.nsu.kosarev.db.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Supplier;

@Component
public class MyOwnTransactionManager {

    @Autowired
    private DataSource dataSource;

    public <T> T transaction(Supplier<T> func) {
        try (Connection connection = dataSource.getConnection()) {
            dataSource.getConnection().setAutoCommit(false);

            T res = func.get();

            connection.commit();
            return res;
        } catch (Throwable th) {
            try {
                dataSource.getConnection().rollback();
            } catch (SQLException ignored) {
            }
            return null;
        }
    }

    public void transaction(Runnable func) {
        try (Connection connection = dataSource.getConnection()) {
            dataSource.getConnection().setAutoCommit(false);

            func.run();

            connection.commit();
        } catch (Throwable th) {
            try {
                dataSource.getConnection().rollback();
            } catch (SQLException ignored) {
            }
        }
    }

}
