package com.mysite.basejava.sql;

import com.mysite.basejava.exception.ExistStorageException;
import com.mysite.basejava.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    private final ConnectionFactory connectionFactory;

    public SqlHelper(final ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T doCommand(final String sqlCommand, final SqlStatement<T> sqlStatement) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand)) {
            return sqlStatement.doCommand(preparedStatement);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new ExistStorageException(null);
            }
            throw new StorageException(e);
        }
    }

    public interface SqlStatement<T> {
        T doCommand(final PreparedStatement preparedStatement) throws SQLException;
    }
}
