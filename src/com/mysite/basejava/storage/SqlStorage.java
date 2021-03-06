package com.mysite.basejava.storage;


import com.mysite.basejava.exception.NotExistStorageException;
import com.mysite.basejava.model.ContactType;
import com.mysite.basejava.model.Content;
import com.mysite.basejava.model.Resume;
import com.mysite.basejava.model.SectionType;
import com.mysite.basejava.sql.SqlHelper;
import com.mysite.basejava.util.JsonParser;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {

    private final SqlHelper sqlHelper;

    public SqlStorage(final String dbUrl, final String dbUser, final String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");    //загружаем драйвер
        } catch (ClassNotFoundException e) {
            e.getMessage();
        }
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
            deleteSection(connection, resume);
            addContacts(connection, resume);
            addSections(connection, resume);
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
            addSections(connection, resume);
            return null;
        });
    }

    @Override
    public Resume get(final String uuid) {
        return sqlHelper.transactionalExecute(connection -> {
            final Resume resume;
            try (PreparedStatement preparedStatement
                         = connection.prepareStatement("" +
                    "SELECT * FROM resume" +
                    " WHERE uuid =?")) {
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, resultSet.getString("full_name"));
            }

            try (PreparedStatement preparedStatement
                         = connection.prepareStatement("" +
                    "SELECT * FROM contact " +
                    " WHERE resume_uuid =?")) {
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    addContact(resultSet, resume);
                }
            }

            try (PreparedStatement preparedStatement
                         = connection.prepareStatement("" +
                    "SELECT * FROM section " +
                    " WHERE resume_uuid =?")) {
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    addSection(resultSet, resume);
                }
            }
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

//    @Override
//    public List<Resume> getAllSorted() {
//        return sqlHelper.doCommand("" +
//                        "SELECT * FROM resume r " +
//                        "  LEFT JOIN contact c ON r.uuid = c.resume_uuid " +
//                        " ORDER BY full_name,uuid",
//                preparedStatement -> {
//                    ResultSet resultSet = preparedStatement.executeQuery();
//                    Map<String, Resume> resumesMap = new LinkedHashMap<>();
//                    while (resultSet.next()) {
//                        String uuid = resultSet.getString("uuid");
//                        String fullName = resultSet.getString("full_name");
//                        addContact(resultSet, resumesMap.computeIfAbsent(uuid, key -> new Resume(key, fullName)));
//                    }
//                    return new ArrayList<>(resumesMap.values());
//                });
//    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(connection -> {
            Map<String, Resume> resumesMap = new LinkedHashMap<>();

            try (PreparedStatement preparedStatement
                         = connection.prepareStatement("" +
                    "SELECT * FROM resume" +
                    " ORDER BY full_name, uuid")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String uuid = resultSet.getString("uuid");
                    resumesMap.put(uuid, new Resume(uuid, resultSet.getString("full_name")));
                }
            }

            try (PreparedStatement preparedStatement
                         = connection.prepareStatement("" +
                    "SELECT * FROM contact")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Resume resume = resumesMap.get(resultSet.getString("resume_uuid"));
                    addContact(resultSet, resume);
                }
            }

            try (PreparedStatement preparedStatement
                         = connection.prepareStatement("" +
                    "SELECT * FROM section")) {
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Resume resume = resumesMap.get(resultSet.getString("resume_uuid"));
                    addSection(resultSet, resume);
                }
            }
            return new ArrayList<>(resumesMap.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.doCommand("" +
                        "SELECT count(*) FROM resume",
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

    private void deleteSection(final Connection connection, final Resume resume) throws SQLException {
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement("" +
                "DELETE FROM section" +
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

    private void addSection(final ResultSet resultSet, final Resume resume) throws SQLException {
        String value = resultSet.getString("value");
        if (value != null) {
            SectionType sectionType = SectionType.valueOf(resultSet.getString("type"));
            resume.setSection(sectionType, JsonParser.read(value, Content.class));
        }
    }

    private void addContacts(final Connection connection, final Resume resume) throws SQLException {
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement("" +
                "INSERT INTO contact (resume_uuid, type, value) " +
                "VALUES (?, ?, ?)")) {
            for (Map.Entry<ContactType, String> entry : resume.getAllContacts().entrySet()) {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, entry.getKey().name());
                preparedStatement.setString(3, entry.getValue());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    private void addSections(final Connection connection, final Resume resume) throws SQLException {
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement("" +
                "INSERT INTO section (resume_uuid, type, value) " +
                "VALUES (?, ?, ?)")) {
            for (Map.Entry<SectionType, Content> entry : resume.getAllSections().entrySet()) {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, entry.getKey().name());
                preparedStatement.setString(3, JsonParser.write(entry.getValue(), Content.class));
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }
}
