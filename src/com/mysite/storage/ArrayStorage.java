package com.mysite.storage;

import com.mysite.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertElement(final Resume resume, final int index) {
        storage[size] = resume;
    }

    @Override
    protected void fillElement(final int index) {
        storage[index] = storage[size];
    }

    protected Integer getSearchElement(final String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}

