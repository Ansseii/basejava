package com.mysite.basejava.storage;


import com.mysite.basejava.exception.NotExistStorageException;
import com.mysite.basejava.model.ContactType;
import com.mysite.basejava.model.Resume;
import com.mysite.basejava.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {

    private final SqlHelper sqlHelper;

    public SqlStorage(final String dbUrl, final String dbUser, final String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.doCommand("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void update(final Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                    "UPDATE resume " +
                    "   SET full_name = ?" +
                    " WHERE uuid = ?")) {
                preparedStatement.setString(1, resume.getFullName());
                preparedStatement.setString(2, resume.getUuid());
                if (preparedStatement.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            deleteContact(connection, resume);
            addContacts(connection, resume);
            return null;
        });
    }

    @Override
    public void save(final Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement preparedStatement
                         = connection.prepareStatement("" +
                    "INSERT INTO resume (uuid, full_name) " +
                    "     VALUES (?, ?)")) {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, resume.getFullName());
                preparedStatement.execute();
            }
            addContacts(connection, resume);
            return null;
        });
    }

    @Override
    public Resume get(final String uuid) {
        return sqlHelper.doCommand("" +
                        "   SELECT * FROM resume r " +
                        "LEFT JOIN contact c " +
                        "       ON r.uuid = c.resume_uuid " +
                        "    WHERE r.uuid =? ",
                preparedStatement -> {
                    preparedStatement.setString(1, uuid);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (!resultSet.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, resultSet.getString("full_name"));
                    do {
                        addContact(resultSet, resume);
                    } while (resultSet.next());
                    return resume;
                });
    }

    @Override
    public void delete(final String uuid) {
        sqlHelper.doCommand("" +
                        "DELETE FROM resume" +
                        " WHERE uuid=?",
                preparedStatement -> {
                    preparedStatement.setString(1, uuid);
                    if (preparedStatement.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.doCommand("" +
                        "SELECT * FROM resume r " +
                        "  LEFT JOIN contact c ON r.uuid = c.resume_uuid " +
                        " ORDER BY full_name,uuid",
                preparedStatement -> {
                    ResultSet resultSet = preparedStatement.executeQuery();
                    Map<String, Resume> resumesMap = new LinkedHashMap<>();
                    while (resultSet.next()) {
                        String uuid = resultSet.getString("uuid");
                        String fullName = resultSet.getString("full_name");
                        addContact(resultSet, resumesMap.computeIfAbsent(uuid, r -> new Resume(uuid, fullName)));
                    }
                    return new ArrayList<>(resumesMap.values());
                });
    }

    @Override
    public int size() {
        return sqlHelper.doCommand("SELECT count(*) FROM resume",
                preparedStatement -> {
                    ResultSet resultSet = preparedStatement.executeQuery();
                    return resultSet.next() ? resultSet.getInt(1) : 0;
                });
    }

    private void deleteContact(final Connection connection, final Resume resume) throws SQLException {
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement("" +
                "DELETE FROM contact" +
                " WHERE resume_uuid=?")) {
            preparedStatement.setString(1, resume.getUuid());
            preparedStatement.execute();
        }
    }

    private void addContact(final ResultSet resultSet, final Resume resume) throws SQLException {
        String value = resultSet.getString("value");
        if (value != null) {
            ContactType contactType = ContactType.valueOf(resultSet.getString("type"));
            resume.setContact(contactType, value);
        }
    }

    private void addContacts(final Connection connection, final Resume resume) throws SQLException {
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)")) {
            for (Map.Entry<ContactType, String> entry : resume.getAllContacts().entrySet()) {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, entry.getKey().name());
                preparedStatement.setString(3, entry.getValue());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }
}
