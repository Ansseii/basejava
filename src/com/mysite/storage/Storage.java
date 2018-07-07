package com.mysite.storage;

import com.mysite.model.Resume;

public interface Storage {

    void clear();

    void update(final Resume resume);

    void save(final Resume resume);

    Resume get(final String uuid);

    void delete(final String uuid);

    Resume[] getAll();

    int size();
}
