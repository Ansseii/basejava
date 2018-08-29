package com.mysite.basejava.storage;

import com.mysite.basejava.exception.NotExistStorageException;
import com.mysite.basejava.exception.StorageException;
import com.mysite.basejava.model.Resume;
import com.mysite.basejava.sql.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {

    private final ConnectionFactory connectionFactory;

    public SqlStorage(final String dbUrl, final String dbUser, final String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("DELETE FROM resume")) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void update(final Resume resume) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
            preparedStatement.setString(1, resume.getFullName());
            preparedStatement.setString(2, resume.getUuid());
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void save(final Resume resume) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?)")) {
            preparedStatement.setString(1, resume.getUuid());
            preparedStatement.setString(2, resume.getFullName());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public Resume get(final String uuid) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement("SELECT * FROM resume r WHERE r.uuid =?")) {
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, resultSet.getString("full_name"));
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void delete(final String uuid) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement("DELETE FROM resume WHERE uuid=?")) {
            preparedStatement.setString(1, uuid);
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement
                     = connection.prepareStatement("SELECT * FROM resume r ORDER BY full_name,uuid")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            while (resultSet.next()) {
                resumes.add(new Resume(resultSet.getString("uuid"), resultSet.getString("full_name")));
            }
            return resumes;
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public int size() {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(*) FROM resume")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? resultSet.getInt(1) : 0;
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
