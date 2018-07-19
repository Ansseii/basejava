package com.mysite.basejava.storage;

import com.mysite.basejava.model.Resume;

import java.util.List;

public interface Storage {

    void clear();

    void update(final Resume resume);

    void save(final Resume resume);

    Resume get(final String uuid);

    void delete(final String uuid);

    List<Resume> getAllSorted();

    int size();
}
