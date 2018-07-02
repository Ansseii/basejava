package com.mysite.storage;

import com.mysite.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void insertElement(final Resume resume, final int index) {
        storage[size] = resume;
    }

    @Override
    protected void fillElement(final int index) {
        storage[index] = storage[size - 1];
    }

    protected int getIndex(final String uuid) {
        for (int i = 0; i < size; i++) {
            if (uuid.equals(storage[i].getUuid())) {
                return i;
            }
        }
        return -1;
    }
}

