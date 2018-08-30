package com.mysite.basejava.storage;


import com.mysite.basejava.exception.NotExistStorageException;
import com.mysite.basejava.model.Resume;
import com.mysite.basejava.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
        sqlHelper.doCommand("UPDATE resume SET full_name = ? WHERE uuid = ?", preparedStatement -> {
            preparedStatement.setString(1, resume.getFullName());
            preparedStatement.setString(2, resume.getUuid());
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
            preparedStatement.execute();
            return null;
        });
    }

    @Override
    public void save(final Resume resume) {
        sqlHelper.doCommand("INSERT INTO resume (uuid, full_name) VALUES (?, ?)", preparedStatement -> {
            preparedStatement.setString(1, resume.getUuid());
            preparedStatement.setString(2, resume.getFullName());
            preparedStatement.execute();
            return null;
        });
    }

    @Override
    public Resume get(final String uuid) {
        return sqlHelper.doCommand("SELECT * FROM resume r WHERE r.uuid =?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, resultSet.getString("full_name"));
        });
    }

    @Override
    public void delete(final String uuid) {
        sqlHelper.doCommand("DELETE FROM resume WHERE uuid=?", preparedStatement -> {
            preparedStatement.setString(1, uuid);
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            preparedStatement.execute();
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.doCommand("SELECT * FROM resume r ORDER BY full_name,uuid", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            while (resultSet.next()) {
                resumes.add(new Resume(resultSet.getString("uuid"), resultSet.getString("full_name")));
            }
            return resumes;
        });
    }

    @Override
    public int size() {
        return sqlHelper.doCommand("SELECT count(*) FROM resume", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() ? resultSet.getInt(1) : 0;
        });
    }
}
